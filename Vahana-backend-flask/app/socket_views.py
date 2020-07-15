from app import socketio, mongo
from mockdb import *
from flask_socketio import emit, send, leave_room, join_room
import json
from flask import request
from pymongo import CursorType
from time import sleep
import threading

@socketio.on('connect')
def greeting():
    print("Connection request received from: ", request.sid)


def serial_fetch_emit(event_name, coll):
    print("Event requested: ", event_name)
    print("Collection passed: ", coll.name)

    while True:
        cursor = coll.find(cursor_type=CursorType.TAILABLE_AWAIT)
        while cursor.alive:
            for doc in cursor:
                doc.pop('_id')
                print(doc)
                emit(event_name, doc)
            socketio.sleep(0.05)


@socketio.on('cameras')
def instant_data(event_name):
    coll_name = event_name.replace('/', "_")
    coll = mongo.db[coll_name]
    serial_fetch_emit(event_name, coll)


@socketio.on('disconnect')
def farewell():
    print("Connection closed: ", request.sid)
