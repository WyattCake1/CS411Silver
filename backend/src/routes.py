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

@main.route('/listings/<int:userId>', methods=['GET'])
def get_match_listings(userId):
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    cursor.execute("SELECT * FROM UserListings WHERE userProfileId != %s",(userId,))
    compare_listings = cursor.fetchall()
    conn.close()
    #TODO call match_listings function and return results
    # results = match_listings(compare_listings, passed in user listing)
    # retrun results

@main.route('/users', methods=['GET'])
def get_users():
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    cursor.execute('SELECT * FROM UserProfiles;')
    users = cursor.fetchall()
    conn.close()
    return jsonify(users)

@main.route('/register', methods=['GET'])
def set_users():
    conn=get_db_connection()

    username = request.args.get('username')
    password = request.args.get('password')
    email = request.args.get('email')

    cursor = conn.cursor(dictionary=True)
    cursor.execute('INSERT INTO UserProfiles (name,password,email) VALUES (%s, %s, %s);', (username,password,email))
    conn.commit()
    conn.close()

    response = {
        "message": f"Hello, {username}. Your password is {password}, your email is {email}."
    }
    return jsonify(response)

@main.route('/login', methods=['GET'])
def check_users():
    conn=get_db_connection()

    username = request.args.get('username')
    password = request.args.get('password')
    

    cursor = conn.cursor(dictionary=True)
    cursor.execute('Select name, password from UserProfiles Where name=%(emp_no)s AND password=%(emp_no2)s ;',{ 'emp_no': username,'emp_no2': password })
    result=cursor.fetchall()
    conn.commit()
    conn.close()

    
    return jsonify(result)



@main.route('/flaskStatus', methods=['GET'])
def flask_status():
    return "Working Good"