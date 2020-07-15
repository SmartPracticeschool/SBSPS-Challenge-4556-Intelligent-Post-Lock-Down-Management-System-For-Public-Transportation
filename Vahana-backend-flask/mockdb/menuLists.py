from mockdb.cameras import *
import copy
menu_items1 = {
    "menuItems": [
        {
            "_id": "entrance",
            "name": "Entrance",

        },
        {
            "_id": "lobby",
            "name": "Lobby",

        },
    ]
}


menuLists = {
    "mastercanteenrailwaystation_train": menu_items1
}


def getMenuItems(station_id):
    if station_id not in menuLists:
        return {"success": False, "message": "Station doesn't exist"}

    menu_list = copy.deepcopy(menuLists[station_id])
    for item in menu_list['menuItems']:
        item['cameras'] = cameras[station_id][item['name']]
    return menu_list
