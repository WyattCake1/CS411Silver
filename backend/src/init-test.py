from flask import Flask, jsonify,request
import mysql.connector
import os

app = Flask(__name__)

def get_db_connection():
    conn = mysql.connector.connect(
        # These can be changed in the docker-compose.yml
        host=os.getenv('DB_HOST', 'db'),
        user=os.getenv('DB_USER', 'testuser'),
        password=os.getenv('DB_PASSWORD', 'testpassword'),
        database=os.getenv('DB_NAME', 'testdb')
    )
    return conn

@app.route('/users', methods=['GET'])
def get_users():
    conn = get_db_connection()
    cursor = conn.cursor(dictionary=True)
    cursor.execute('SELECT * FROM users;')
    users = cursor.fetchall()
    conn.close()
    return jsonify(users)

@app.route('/register', methods=['GET'])
def set_users():
    conn=get_db_connection()

     # Retrieve query parameters
    username = request.args.get('username')
    password = request.args.get('password')
    email = request.args.get('email')

    cursor = conn.cursor(dictionary=True)
    cursor.execute('INSERT INTO users (name,password,email) VALUES (%s, %s, %s);', (username,password,email))
    conn.commit()
    conn.close()

    # Process the data or respond with the received data
    response = {
        "message": f"Hello, {username}. Your password is {password}, your email is {email}."
    }
    return jsonify(response)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
