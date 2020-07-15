from flask import Flask
from flask_socketio import SocketIO
from flask_cors import CORS
from flask_pymongo import PyMongo

app = Flask(__name__)

app.config['SECRET_KEY'] = 'mysecret'
app.config['CORS_HEADERS'] = 'Content-Type'
app.config['MONGO_DBNAME'] = 'vahanaDB'
app.config['MONGO_URI'] = 'mongodb://localhost:27017/vahanaDB'
cors = CORS(app)
mongo = PyMongo(app)

socketio = SocketIO(app, cors_allowed_origins="*", async_mode='eventlet')

from app import schedule_views, socket_views, passenger_views, menu_views, stats_views, admin_views

