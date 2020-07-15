from pymongo import MongoClient
import time
from cv2 import cv2
import os
from imutils.video import FPS
from imutils.video import VideoStream
import base64
import requests
import json
from scipy.spatial import distance as dist
import numpy as np

MASK_DOCKER_URL = 'http://localhost:{}/v1/models/default:predict'.format("port_number")
SOCIAL_DISTANCE_DOCKER_URL = 'http://localhost:{}/v1/models/default:predict'.format("port_number")
MIN_DISTANCE = 50
RTSP_URL = ""


def analyse(station_id, cam_area, cam_name, cam_ip):
    print("New process started")
    global RTSP_URL
    RTSP_URL = cam_ip
    # connect to client, define collection names
    client = MongoClient('localhost', 27017)
    db = client.vahanaDB

    base = '_data_' + ''.join(cam_area.split()).lower() + '_' + ''.join(cam_name.split()).lower()
    base_v = '_video_' + ''.join(cam_area.split()).lower() + '_' + ''.join(cam_name.split()).lower()

    avg_coll_name = station_id + base + '_average'
    inst_coll_name = station_id + base + '_instant'
    img_coll_name = station_id + base_v

    # create collections, access if already created

    # image collection
    image_collection = None
    try:
        image_collection = db.create_collection(
            name=station_id,
            capped=True,
            size=20212880,
            max=3
        )
        print("Image collection created: ", image_collection.name)
    except Exception as err:
        # collection already exists
        if "already exists" in err:
            image_collection = db[img_coll_name]
            print("Image collection already exists")
        else:
            print("Image create_collection() ERROR:", err)

    # instantaneous data collection
    inst_collection = None
    try:
        inst_collection = db.create_collection(
            name=inst_coll_name,
            capped=True,
            size=5242880,
            max=1
        )
        print("Instant collection created: ", inst_collection.name)
    except Exception as err:
        # collection already exists
        if "already exists" in err:
            inst_collection = db[inst_coll_name]
            print("Instant collection already exists")
        else:
            print("Instant create_collection() ERROR:", err)

    # time averaged data collection
    avg_collection = None
    try:
        avg_collection = db.create_collection(
            name=avg_coll_name,
            capped=True,
            size=5242880,
            max=1
        )
        print("Average collection created: ", avg_collection.name)
    except Exception as err:
        # collection already exists
        if "already exists" in err:
            avg_collection = db[avg_coll_name]
            print("Average collection already exists")
        else:
            print("Average create_collection() ERROR:", err)

    # start analysing video stream frame wise and populate above collections
    vs = cv2.VideoCapture(RTSP_URL)
    time.sleep(2.0)
    fps = FPS().start()
    # writer = None
    (W, H) = (None, None)

    cnt=0
    color = (255, 0, 0)
    is_processing_flag = False

    # loop over frames from the video file stream
    while True:
        if is_processing_flag:
            return

        is_processing_flag = True
        cnt += 1
        # read the next frame from the file
        (grabbed, frame) = vs.read()

        # if the frame was not grabbed, then we have reached the end
        # of the stream
        if not grabbed:
            break
        # if the frame dimensions are empty, grab them
        if W is None or H is None:
            (H, W) = frame.shape[:2]

        (mask_violation, violation_pairs) = process_image(frame, cnt)

        violation_pairs  # Total social distancing violation pairs
        mask_violation  # Total mask violation count

        encoded_string = base64.b64encode(frame)
        # print(encoded_string)
        abc = image_collection.insert(
            {
                "station_id": station_id,
                "cam_ip": cam_ip,
                "cam_name": cam_name,
                "cam_area": cam_area,
                "image": encoded_string
            }
        )
        # print(abc)
        # show the output frame
        # cv2.imshow("Frame", cv2.resize(frame, (800, 600)))
        key = cv2.waitKey(1) & 0xFF
        # print ("key", key)
        # if the `q` key was pressed, break from the loop
        if key == ord("q"):
            break

        # update the FPS counter
        fps.update()
        print("Count is : ", cnt)
        is_processing_flag = False

    # stop the timer and display FPS information
    fps.stop()

    print("[INFO] elapsed time: {:.2f}".format(fps.elapsed()))
    print("[INFO] approx. FPS: {:.2f}".format(fps.fps()))

    # do a bit of cleanup
    cv2.destroyAllWindows()
    # release the file pointers
    print("[INFO] cleaning up...")
    # writer.release()
    vs.release()

    # insert random non img documents (meant to be done inside previous while loop, using model
    # inferences to populate the document)
    if inst_collection is not None:
        inst_data_doc = {
            "tiles": [
                {
                    "title": "Crowd Density",
                    "value": 7,
                },
                {
                    "title": "Social Distance violations",
                    "value": 5,
                },
                {
                    "title": "Mask violations",
                    "value": 2,
                },
            ]
        }
        insert_id = inst_collection.insert_one(inst_data_doc).inserted_id
        print("Instant document inserted with IDs: ", insert_id)

    if avg_collection is not None:
        avg_data_doc = {
            "tiles": [
                {
                    "title": "Average Crowd Density",
                    "value": 2,
                },
                {
                    "title": "Average Social Distance violations",
                    "value": 3,
                },
                {
                    "title": "Average Mask violations",
                    "value": 4,
                },
            ]
        }

        insert_id = avg_collection.insert_one(avg_data_doc).inserted_id
        print("Average document inserted with ID: ", insert_id)


def container_predict(image, image_key, url):

    encoded_image = base64.b64encode(image).decode('utf-8')

    instances = {
            'instances': [
                    {'image_bytes': {'b64': str(encoded_image)},
                     'key': image_key}
            ]
    }

    response = requests.post(url, data=json.dumps(instances))
    print(response.json())
    return response.json()


def process_image(frame, cnt):
    global MASK_DOCKER_URL, SOCIAL_DISTANCE_DOCKER_URL, MIN_DISTANCE
    mask_pred_output = json.loads(container_predict(frame, str(cnt), MASK_DOCKER_URL))
    mask_violation = 0

    for x in mask_pred_output:
        if x["label"] == "unmasked":
            mask_violation += 1

    social_pred_output = json.loads(container_predict(frame, str(cnt), SOCIAL_DISTANCE_DOCKER_URL))

    for x in social_pred_output:
        temp = ((x["box"]["left"] + (x["box"]["width"] / 2)), (x["box"]["top"] + (x["box"]["height"] / 2)))
        x["centroid"] = temp

    centroids = np.array([r["centroid"] for r in social_pred_output])
    D = dist.cdist(centroids, centroids, metric="euclidean")
    violate = set()
    violation_pairs = 0

    for i in range(0, D.shape[0]):
        for j in range(i + 1, D.shape[1]):
            if D[i, j] < MIN_DISTANCE:
                violate.add(i)
                violate.add(j)
                violation_pairs += 1

    temp_count = 0
    for x in social_pred_output:
        color = (0, 255, 0)
        if temp_count in violate:
            color = (0, 0, 255)
        temp_count += 1
        (x, y) = (x["box"]["left"], x["box"]["top"])
        (w, h) = (x["box"]["width"], x["box"]["height"])
        cv2.rectangle(frame, (x, y), (x + w, y + h), color, 2)
        cv2.circle(frame, x["centroid"], 5, color, 1)
        text = "{}: {:.4f}".format(x["label"], x["score"])
        cv2.putText(frame, text, (x, y - 5), cv2.FONT_HERSHEY_SIMPLEX, 0.5, color, 2)

    return mask_violation, violation_pairs
