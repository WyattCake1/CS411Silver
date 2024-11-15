from flask import (Blueprint, request, jsonify)
from db import get_db_connection

main = Blueprint("main",__name__)

@main.route('/listings/<int:userId>', methods=['GET'])
def get_user_listings(userId):
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    cursor.execute("SELECT * FROM UserListings WHERE userProfileId = %s",(userId,))
    listings = cursor.fetchall()
    conn.close()
    return jsonify(listings)

@main.route('/listings', methods=['POST'])
def save_user_listings():
    data = request.get_json()
    
    required_fields = ["campaign", "gameName", "environment", "startTime", "endTime", "difficulty", "role", "userProfileId"]
    for field in required_fields:
        if field not in data:
            return jsonify({"error": f"Missing required field: {field}"}), 400
    
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    cursor.execute(
        "INSERT INTO UserListings (campaign, gameName, environment, startTime, endTime, difficulty, role, userProfileId)"
        "VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
        (
            data["campaign"],
            data["gameName"],
            data["environment"],
            data["startTime"],
            data["endTime"],
            data["difficulty"],
            data["role"],
            data["userProfileId"]
        )
    )
    conn.commit()
    listing_id = cursor.lastrowid
    conn.close()

    return jsonify({"message": "Listing saved", "listing_id": listing_id}),201



@main.route('/flaskStatus', methods=['GET'])
def flask_status():
    return "Working Good"