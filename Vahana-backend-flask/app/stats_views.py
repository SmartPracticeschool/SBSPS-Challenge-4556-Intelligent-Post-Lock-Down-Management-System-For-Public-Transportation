from app import app
from flask import jsonify, request

from mockdb import *


@app.route('/stats/bookings/<string:station_id>', methods=['GET'])
def booking_details(station_id):
    print("request")
    if request.method == 'GET':
        if station_id != "":
            count = 0
            for booking in bookings:
                if booking["station_id"] == station_id:
                    count += 1
            return str(count)
        else:
            return str(0)
    else:
        return str(0)


@app.route('/stats/<string:station_id>/<string:date>/graphs/<string:graph_name>', methods=['GET'])
def hub_stats_graph(station_id, graph_name, date):
    if request.method == 'GET':
        for graph in bar_graph_data[station_id]:
            if graph["_id"] == graph_name:
                return jsonify(graph)


@app.route('/stats/<string:station_id>/<string:date>', methods=['GET'])
def hub_stats(station_id, date):
    if request.method == 'GET':
        return jsonify(stats_data[station_id])


