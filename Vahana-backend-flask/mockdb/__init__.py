from mockdb.stats import *
from mockdb.admins import *
from mockdb.schedule import *
from mockdb.stations import *
from mockdb.menuLists import *
from mockdb.cameras import *

license_nos = {"1234": True, "3456": False}

passenger = [{"id": 1, "Name": "Abhishek", "Email": "mehariaabhishek@gmail.com",
              "password": "abhi", "is_safe":True},
             {"id": 2, "Name": "Abhilash", "Email": "abhi@gmail.com",
              "password": "abhi", "is_safe":False}]

passenger_bookings = [{"id": 1, "passenger_id": 1, "vehicle_id": 1, "date": "24/11/2020"},
                      {"id": 2, "passenger_id": 1, "vehicle_id": 2, "date": "24/11/2020"},
                      {"id": 3, "passenger_id": 2, "vehicle_id": 3, "date": "24/11/2020"},
                      {"id": 4, "passenger_id": 2, "vehicle_id": 2, "date": "24/11/2020"}
                      ]

travel_details = [{"id": 1, "booking_id": 1, "name": "Abhishek", "age": 45, "sex": 'M'},
                  {"id": 2, "booking_id": 1, "name": "Abhi", "age": 45, "sex": 'F'},
                  {"id": 3, "booking_id": 3, "name": "Abhishek", "age": 45, "sex": 'M'},
                  {"id": 4, "booking_id": 3, "name": "Abhi", "age": 45, "sex": 'F'}
                  ]

hotspot_areas = [{"lat": 132, "lng": 152, "radius": 40000},
                 {"lat": 126, "lng": 152, "radius": 40000},
                 {"lat": 110, "lng": 122, "radius": 40000},
                 {"lat": 162, "lng": 142, "radius": 40000},
                 {"lat": 82, "lng": 132, "radius": 40000},
                 {"lat": 24, "lng": 87, "radius": 400000}]


bookings = [
    {"station_id": "station3_train"},
    {"station_id": "station2_flight"},
    {"station_id": "station1_bus"},
    {"station_id": "station1_bus"},
    {"station_id": "station1_bus"},
    {"station_id": "station1_bus"},
    {"station_id": "station1_bus"},
    {"station_id": "station1_bus"},
]

x = {
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
    ],
}
x2 = {
    "tiles": [
        {
            "title": "Crowd Density",
            "value": 3,
        },
        {
            "title": "Social Distance violations",
            "value": 8,
        },
        {
            "title": "Mask violations",
            "value": 4,
        },
    ],
}
