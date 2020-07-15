from app import app
from flask import jsonify, request

from mockdb import *
import copy


@app.route('/users/<string:username>/<string:password>', methods=['GET'])
def login_check(username, password):
    for user in users:
        if user["username"] == username and user["password"] == password:
            print(username, " logged in...")

            for station in stations:
                print(station["_id"], user["station_id"])
                if station["_id"] == user["station_id"]:
                    user_copy = copy.deepcopy(user)
                    user_copy["station"] = station
                    user_copy.pop("station_id")
                    return jsonify({'success': True, "details": user_copy})
    return jsonify({'success': False})


@app.route('/users/adduser/<string:license_num>', methods=['POST', 'OPTIONS'])
def add_new_user(license_num):
    data = request.json
    print("data from registration: ", data)

    username = data["newuser"]["username"]
    password = data["newuser"]["password"]
    name = data["newuser"]["name"]
    station = data["newuser"]["station"]

    if license_num in license_nos.keys():
        if not license_nos[license_num]:
            # add user
            for user in users:
                if user["username"] == username:
                    return jsonify({'success': False, "message": "This username is already taken"})
            new_user = {
                "username": username,
                "password": password,
                "items": {"menuItems": []},
                "license_num": license_num,
                "name": name,
                "station_id": station["_id"]
            }
            users.append(new_user)
            license_nos[license_num] = True

            station_found = False
            for temp in stations:
                if temp["_id"] == station["_id"]:
                    station_found = True
                    break
            if not station_found:
                stations.append(station)

            new_user.pop("station_id")
            new_user["station"] = station
            return jsonify({'success': True, "details": new_user})
        else:
            return jsonify({'success': False, "message": "License number already taken"})
    else:
        return jsonify({'success': False, "message": "License number doesn't exist"})

