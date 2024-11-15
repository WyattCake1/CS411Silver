from flask import g
import os
import mysql.connector

def get_db_connection():
    if "db" not in g or not g.db.is_connected():
        g.db = mysql.connector.connect(
        # These can be changed in the docker-compose.yml
            host=os.getenv('DB_HOST', 'db'),   
            user=os.getenv('DB_USER', 'testuser'),
            password=os.getenv('DB_PASSWORD', 'testpassword'),
            database=os.getenv('DB_NAME', 'rolecall_database')
    )
    return g.db

def close_db_connection(e=None):
    db = g.pop("db",None)

    if db is not None:
        db.close()

def init_app(app):
    app.teardown_appcontext(close_db_connection)