from flask import Flask
import db
from routes import main

def start_app():
    app = Flask(__name__)
    db.init_app(app)
    app.register_blueprint(main)

    return app

if __name__ == '__main__':
    start_app().run(host='0.0.0.0', port=5000, debug=True)