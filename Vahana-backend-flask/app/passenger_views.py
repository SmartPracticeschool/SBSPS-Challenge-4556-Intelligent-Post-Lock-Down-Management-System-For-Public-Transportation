from app import app
from flask import jsonify, request

from mockdb import *
# importing the requests library
import requests

# api-endpoint
URL = "https://matrixfrats.com/screenie/backend/qr.php?text="
headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}


@app.route('/app/signin', methods=['POST'])
def passenger_signin():
    if request.method == 'POST':
        data = request.json
        print(data)
        email = data["email"]
        password = data["password"]
        for user in passenger:
            if user["Email"] == email and user["password"] == password:
                return jsonify({'success': True, "status": "okay"})
            else:
                return jsonify({'success': False, "status": "404 error"})
    return jsonify({'success': False, "status": "Bad request"})


@app.route('/app/signup', methods=['POST'])
def passenger_signup():
    if request.method == 'POST':
        data = request.json
        print(data)
        email = data["email"]
        name = data["name"]
        password = data["password"]
        for user in passenger:
            if user["Email"] == email:
                return jsonify({'success': False, "message": "This username is already taken"})

            new_user = {"id": (len(passenger) + 1), "Name": name, "Email": email,
                        "password": password, "is_safe": True}
            passenger.append(new_user)
            return jsonify({'success': True, "message": new_user})
    return jsonify({'success': False, "message": "Bad request"})


@app.route('/app/station/<string:search>', methods=['GET'])
def p_station_search(search):
    if request.method == 'GET':
        if search != "":
            return jsonify({'success': True, "data": stations})
        else:
            return jsonify({'success': True, "data": "Not Found"})
    else:
        return jsonify({'success': False, "data": "Bad request"})


@app.route('/app/search', methods=['POST'])
def p_vehicle_search():
    if request.method == 'POST':
        data = request.json
        print(data)
        type = data["type"]
        source = data["source"]
        destination = data["destination"]
        date = data["date"]
        veh = []
        for vehicle in vehicles:
            if vehicle["type"] == type and vehicle["source"] == source and vehicle["destination"] == destination:
                veh.append(vehicle)
        if len(veh) > 0:
            return jsonify({'success': True, "data": veh})
        else:
            return jsonify({'success': True, "data": "Not Found"})

    return jsonify({'success': False, "data": "Bad request"})


@app.route('/app/book', methods=['POST'])
def p_vehicle_book():
    if request.method == 'POST':
        data = request.json
        print(data)
        email = data["email"]
        vid = data["vid"]
        date = data["date"]
        details = data["details"]
        for user in passenger:
            if user["Email"] == email:
                user_id = user["id"]
                d_count = len(passenger_bookings) + 1
                new_booking = {"id": d_count, "passenger_id": user_id, "vehicle_id": vid, "date": date}
                passenger_bookings.append(new_booking)
                for detail in details:
                    t_count = len(travel_details) + 1
                    new_detail = {"id": t_count, "booking_id": d_count,
                                  "name": detail["name"], "age": detail["age"], "sex": detail["sex"]}
                    travel_details.append(new_detail)
                return jsonify({'success': True, "status": "okay"})
    return jsonify({'success': False, "status": "Bad request"})


@app.route('/app/booked', methods=['POST'])
def p_vehicle_booked():
    if request.method == 'POST':
        data = request.json
        print(data)
        print('email')
        email = data["email"]
        for user in passenger:
            if user["Email"] == email:
                user_id = user["id"]
                result = []
                for booking in passenger_bookings:
                    temp = dict()
                    if booking["passenger_id"] == user_id:
                        travel_temp = []
                        vid = booking["vehicle_id"]
                        for vehicle in vehicles:
                            if vehicle["vehicle_id"] == vid:
                                temp["v_detail"] = vehicle
                        for travel_detail in travel_details:
                            if booking["id"] == travel_detail["booking_id"]:
                                travel_temp.append(travel_detail)
                        temp["p_detail"] = travel_temp
                        temp["date"] = booking["date"]
                        temp["id"] = booking["id"]
                        #temp["qr"] = ""
                        temp["qr"] = str(requests.get(url=(URL + str(temp["id"])), headers = headers).content)
                        result.append(temp)
                print(result)
                return jsonify({'success': True, "status": result})
    return jsonify({'success': False, "status": "Bad request"})


@app.route('/app/hotspot', methods=['GET'])
def p_hotspot():
    if request.method == 'GET':
        return jsonify({'success': True, "data": hotspot_areas})
    else:
        return jsonify({'success': False, "data": "Bad request"})


@app.route('/app/safeflag', methods=['POST'])
def passenger_safeflag():
    if request.method == 'POST':
        data = request.json
        print(data)
        email = data["email"]
        flag = data["flag"]
        for user in passenger:
            if user["Email"] == email:
                user["is_safe"] = flag
                return jsonify({'success': True, "status": "okay"})
    return jsonify({'success': False, "status": "Bad request"})

