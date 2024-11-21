-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS rolecall_database;

-- Use the created or existing database
USE rolecall_database;

-- Table for UserProfiles
CREATE TABLE IF NOT EXISTS UserProfiles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- Table for UserListings
CREATE TABLE IF NOT EXISTS UserListings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    campaign BOOLEAN NOT NULL,
    gameName VARCHAR(255) NOT NULL,
    environment VARCHAR(100) NOT NULL,
    startTime VARCHAR(100),
    endTime VARCHAR(100),
    difficulty VARCHAR(100),
    role VARCHAR(100),
    userProfileId INT,
    FOREIGN KEY (userProfileId) REFERENCES UserProfiles(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Test Inserts
INSERT INTO UserProfiles (name,password,email) VALUES
    ('Test User','testPass', 'user@test.com'),
    ('Berhans','BEESecret','bclar006@gmail.com');

INSERT INTO UserListings (campaign, gameName, environment, startTime, endTime, difficulty, role, userProfileId) VALUES
    (TRUE, 'Dnd', 'Online', "1000", "2000", 'Hard', 'Healer', 1),
    (FALSE, 'Dnd', 'In-Person', "1000", "2000", 'Hard', 'Tank', 1),
    (TRUE, 'Warhammer', 'Online', "1000", "2000", 'Hard', 'Rogue', 1);
