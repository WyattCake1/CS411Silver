from flask import Flask,jsonify

app = Flask(__name__)

listings = [
    {"id":1,"name":"Pathfinder"},
    {"id":2,"name":"Dungeons and Dragons"},
    {"id":3,"name":"Cyberpunk RED"},
    {"id":4,"name":"Call of Cuthulu"},
]

@app.route('/listings', methods=['GET'])
def get_listings():
    return jsonify(listings)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
