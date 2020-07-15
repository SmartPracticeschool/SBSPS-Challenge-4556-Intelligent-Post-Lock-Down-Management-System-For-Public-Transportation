from app import app
from flask import jsonify, request
from flask_cors import cross_origin

from mockdb import *

from multiprocessing import Process
from visual_stack import analyse


@app.route('/menu/<string:station_id>', methods=['GET'])
def menu(station_id):
    return jsonify(getMenuItems(station_id))


@app.route('/menu/addcam/<string:station_id>', methods=['POST', 'OPTIONS'])
@cross_origin(origin='localhost', headers=['Content- Type', 'Authorization'])
def add_item(station_id):
    print(station_id)

    if station_id not in cameras:
        print("Station Not found")
        return jsonify({'success': False, 'message': "Station doesn't exist"})

    print("Station found")
    data = request.json
    print(data)
    cam_area = data["values"]["area"]
    cam_name = data["values"]["camname"]
    cam_ip = data["values"]["camip"]
    cam_id = cam_area.lower().replace(" ", "") + "_" + cam_name.lower().replace(" ", "")

    if cam_area in cameras[station_id]:
        for camera in cameras[station_id][cam_area]:
            if cam_name == camera["name"]:
                print("Camera found")
                return jsonify({'success': False,
                                'message': data["values"]["camname"] + " already exists in " +
                                           data["values"]["area"]})
    else:
        cameras[station_id][cam_area] = []
        menuLists[station_id]['menuItems'].append({
            "_id": cam_area.lower().replace(" ", ""),
            "name": cam_area,
            "cameras": []
        })

    cameras[station_id][cam_area].append({
        "_id": cam_id,
        "name": cam_name,
        "camip": cam_ip
    })

    # generate visual stack process
    p1 = Process(target=analyse, args=[station_id, cam_area, cam_name, cam_ip])
    p1.start()

    return jsonify({'success': True, "menuItems": getMenuItems(station_id)})
