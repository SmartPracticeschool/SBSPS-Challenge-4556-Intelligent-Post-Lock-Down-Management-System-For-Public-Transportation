from mockdb import *

empty_stream = {
    "tiles": [
        {
            "title": "Crowd Density",
            "value": 0,
        },
        {
            "title": "Social Distance violations",
            "value": 0,
        },
        {
            "title": "Mask violations",
            "value": 0,
        },
    ],
}


def stream_creator(station_id, cam_area, cam_name):
    base = '/data/' + ''.join(cam_area.split()).lower() + '/' + ''.join(cam_name.split()).lower()
    event_name = base + '/instant'
    streams[station_id][event_name] = empty_stream
    event_name = base + '/average'
    streams[station_id][event_name] = empty_stream

