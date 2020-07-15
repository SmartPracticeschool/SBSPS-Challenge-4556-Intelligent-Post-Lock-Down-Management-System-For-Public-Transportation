
actions = ["Disinfect", "Running Status", "Cancel"]
safety_status = ["good", "okay", "unsafe"]
vehicle_type = ["bus", "train", "flight"]


vehicles = [
{"key": '3', "type": vehicle_type[2], "vehicle_id": 3, "vehicle_name": "kgm", "arrival_time": "10:30 AM", "departure_time": "11:00 AM", "occupancy": 300,
     "source": "agc", "destination": "pql", "safety_status": safety_status[1], "actions": [actions[1]]},
    {"key": '1', "type": vehicle_type[0], "vehicle_id": 1, "vehicle_name": "XYZ", "arrival_time": "10:30 AM", "departure_time": "11:00 AM", "occupancy": 300,
     "source": "abc", "destination": "pqr", "safety_status": safety_status[1], "actions": [actions[1]]},
    {"key": '2', "type": vehicle_type[1], "vehicle_id": 2, "vehicle_name": "klm", "arrival_time": "10:30 AM", "departure_time": "11:00 AM", "occupancy": 300,
     "source": "abc", "destination": "pqr", "safety_status": safety_status[1], "actions": [actions[1]]},

]