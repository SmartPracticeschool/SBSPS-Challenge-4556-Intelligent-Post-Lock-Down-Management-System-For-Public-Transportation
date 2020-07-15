from app import app
from flask import jsonify, request

from mockdb import *


@app.route('/schedule/<string:station_id>', methods=['GET'])
def schedule(station_id):
    print("request")
    if request.method == 'GET':
        if station_id != "":
            for user in users:
                if user["station"]["_id"] == station_id:
                    return jsonify(vehicles)
            return jsonify({})
        else:
            return jsonify({})
    else:
        return jsonify({})



#
# @app.route('/menu/addcam/<string:username>/<string:password>', methods=['POST', 'OPTIONS'])
# def add_item(username, password):
#     if request.method == 'POST':
#         if username != "" and password != "":
#             print(username, password)
#             for user in users:
#                 if user["username"] == username and user["password"] == password:
#                     print("User found")
#                     print(request)
#                     data = request.json
#                     print(data)
#                     area_f = 0
#                     cam_f = 0
#                     for data_list in user["items"]["menuItems"]:
#
#                         if data["values"]["area"] == data_list["name"]:
#                             area_f = 1
#                             print("Area found")
#                             for camera in data_list["cameras"]:
#                                 if camera["name"] == data["values"]["camname"]:
#                                     cam_f = 1
#                                     print("Camera found")
#
#                             if cam_f == 0:
#                                 cam_name = data["values"]["area"].lower().replace(" ", "") + "_" + data["values"][
#                                     "camname"].lower().replace(" ", "")
#                                 data_list["cameras"].append({"_id": cam_name, "name": data["values"]["camname"],
#                                                              "camip": data["values"]["camip"]})
#                                 return jsonify({'success': True, "menuItems": user["items"]})
#                             else:
#                                 return jsonify({'success': False,
#                                                 'message': data["values"]["camname"] + " already exists in " +
#                                                            data["values"]["area"]})
#                     print("Area Not found")
#
#                     if area_f == 0:
#                         cam_name = data["values"]["area"].lower().replace(" ", "") + "_" + data["values"][
#                             "camname"].lower().replace(" ", "")
#                         user["items"]["menuItems"].append({
#                             "_id": data["values"]["area"].lower(),
#                             "name": data["values"]["area"],
#                             "cameras": [
#                                 {
#                                     "_id": cam_name,
#                                     "name": data["values"]["camname"],
#                                     "camip": data["values"]["camip"],
#                                 }
#                             ]
#                         })
#                         return jsonify({'success': True, "menuItems": user["items"]})
#             print("User Not found")
#             return jsonify({'success': False})
#         else:
#             return jsonify({'success': False})
#     else:
#         return jsonify({'success': False})
#
#
# @app.route('/menu/<string:username>/<string:password>', methods=['GET'])
# def menu_details(username, password):
#     print("request")
#     if request.method == 'GET':
#         if username != "" and password != "":
#             for user in users:
#                 if user["username"] == username and user["password"] == password:
#                     return jsonify(user["items"])
#             return jsonify({})
#         else:
#             return jsonify({})
#     else:
#         return jsonify({})
#
