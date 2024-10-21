# Docker - Python Backend with MySQL Setup

## Acknowledgments
Thank you to Docker for creating the [Docker workshop](https://docs.docker.com/get-started/workshop/). This information was critical for understanding and setting up this configuration. 

## Prerequisites
Make sure you have these installed

[Docker](https://www.docker.com/products/docker-desktop/)

[Docker Compose](https://docs.docker.com/compose/install/)

### Backend Configuration
The Python backend is located in the `backend/` folder and is built using the `Dockerfile`. The backend will run a Flask web server that communicates with the MySQL db. 

### Database Configuration
The database credentials and other settings are found in the 
`docker-compose.yml` file. Docker automatically manages this Volume 

### Docker Compose
Docker Compose allows us to run the Flask web server and MySQL database in seperate containers

## Project Structure
```
CS411SILVER/
├── backend/
│   ├── Dockerfile           # Dockerfile for Flask backend
│   ├── requirements.txt     # Python dependencies
│   ├── src/
│   │   └── app.py           # Flask app code
│   └── tests/               # Unit tests for the backend
├── database/                # Directory for database-related files
│   ├── data/                # MySQL data will be stored here
├── docker/
│   ├── docker-compose.yml   # Docker Compose setup
│   ├── README.md            # This README.md 
├── frontend/
│   ├── lib/
│   │   └── main.dart        # Flutter app code
├── scripts/                 # Scripts used in the project
```
#### Build and run the containers
Make sure to run this command while in the `docker/` directory.
```
docker-compose up --build
```
- This builds the backend container.
- Pulls the mysql:latest image fro the MySQL database
- Spins up both containers and links them so they can communicate 

#### Access the Flask App
```
http://localhost:5000
```

#### Connect to the MySQL Database:
The database will be running on port ```3306```. You can connect using, 
```
Host: localhost
Port: 3306
Username: testuser
Password: testpassword
```
These credentials are set in the docker-compose.yml file located in the Docker directory. 

#### Backend Test
This tests the communication between the Flask container and the MySQL container. 
- Build containers with `docker-compose up --build`
- Run test with `curl http://localhost:5000/users`

##### Expected Output:
```
[{"id":1,"name":"Berhan"},{"id":2,"name":"Bradley"},{"id":3,"name":"Chris"},{"id":4,"name":"Sebastian"},{"id":5,"name":"Wyatt"}]
```

#### Workflows 
You can make changes to the backend and those changes will be reflected in the running Docker container thanks to [volume mounting](https://docs.docker.com/engine/storage/volumes/).

To rebuild and restart containers use:
```
docker-compose down
docker-compse up --build
```

To stop the containers use:
```
docker-compose stop
```

To remove the containers use:
```
docker-compose down
```

#### Adding New Depedencies 
Add all Python depedencies to:
```
backed/requirements.txt
```

Rebuild container using:
```
docker-compose up --build
```

#### Running Backend Tests
Tests can be places in the `backend/tests` directory.

#### MySQL Data 
The data is stored in a Docker volume, which ensures that the data is persisted across container restarts. The volume is defined in ```docker-compose.yml``` and can be found at `/var/lib/docker/volumes/` on Linux/Mac or similar path on Windows. 

List and inspect volumes using: 

```docker volume ls```


# Reference
- [Docker Workshop](https://docs.docker.com/get-started/workshop/)
- [Docker: MySQL Documentation](https://hub.docker.com/_/mysql/)
- [Docker: Network Documentation](https://docs.docker.com/engine/network/)

Below are my notes when working through the Docker workshop module. 

This assumes you have a project folder that contains a Docker file. The getting-started stuff is from the test program I used in the workshop.

#### Build image
```
docker build -t getting-started .
```
#### Run container
```
docker run -dp 127.0.0.1:3000:3000 getting-started
```
#### List containers: 
Useful for getting a containers `id`
```
docker ps
```
#### Stop container
```
docker stop <id>
```
#### Remove container
```
docker rm -f <id>
```
#### Create a volume
```
docker volume create rolecall-db
```
#### Start container w/volume
```
docker run -dp 127.0.0.1:3000:3000 --mount type=volume,src=rolecall-db,target=/etc/rolecall getting-started
```
#### Where is Docker storing my volume?
```
docker volume inspect rolecall-db
```
How can we allow one container to talk to another?
#### Create a network
```
docker network create rolecall-app
```
#### Start MySQL container and Attach to Network
```
docker run -d \
    --network rolecall-app --network-alias mysql \
    -v todo-mysql-data:/var/lib/mysql \
    -e MYSQL_ROOT_PASSWORD=secret \
    -e MYSQL_DATABASE=roelcall \
    mysql:8.0
```
#### Verify Database is running
```
docker exec -it <mysql-container-id> mysql -u root -p
```
Each container has its own IP address
#### Get IP of container
Install DNS tool
```
docker run -it --network rolecall-app nicolaka/netshoot
```
Lookup IP: 
```
dig mysql
```
Then look for,
```
ANSWER SECTION
```
#### Start Development Container
````
docker run -dp 127.0.0.1:3000:3000 \
  -w /app -v "$(pwd):/app" \
  --network rolecall-app \
  -e MYSQL_HOST=mysql \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=secret \
  -e MYSQL_DB=rolecall \
  node:18-alpine \
  sh -c "yarn install && yarn run dev"
```
