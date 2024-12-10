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
    day VARCHAR(100),
    startTime VARCHAR(100),
    endTime VARCHAR(100),
    difficulty VARCHAR(100),
    role VARCHAR(100),
    userProfileId INT,
    FOREIGN KEY (userProfileId) REFERENCES UserProfiles(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- User Profiles
INSERT INTO UserProfiles (name,password,email) VALUES
    ('dev',SHA2('dev',256), 'dev'),
    ('Berhansz',SHA2('BEESecret',256),'bclar006@gmail.com'),
    ('cake',SHA2('cake',256),'cake@mail.com');

 -- User 1 (dev)
INSERT INTO UserListings (campaign, gameName, environment, day, startTime, endTime, difficulty, role, userProfileId) VALUES
    (TRUE, 'Dnd', 'Online', 'Fri', '5:30 PM', '8:30 PM', 'Casual', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 1),
    (FALSE, 'Dnd', 'In-Person', 'Tue', '4:00 PM', '7:00 PM', 'Intermediate', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 1),
    (TRUE, 'Warhammer', 'Online', 'Sat', '10:00 AM', '2:00 PM', 'Intermediate', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 1);
-- User 2 (Berhan)
INSERT INTO UserListings (campaign, gameName, environment, day, startTime, endTime, difficulty, role, userProfileId) VALUES
    (FALSE, 'Dnd', 'Online', "Fri", "5:30 PM", "8:30 PM", 'First Game', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 2),
    (TRUE, 'Dnd', 'In-Person', "Tue", "4:30 PM", "7:00 PM", 'Intermediate', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 2),
    (FALSE, 'Warhammer', 'Online', "Wed", "6:00 PM", "9:30 PM", 'Casual', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 2);
-- User 3 (Wyatt)
INSERT INTO UserListings (campaign, gameName, environment, day, startTime, endTime, difficulty, role, userProfileId) VALUES
    (FALSE, 'Dnd', 'Online', "Fri", "5:00 PM", "9:00 PM", 'Casual', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 3),
    (FALSE, 'Warhammer', 'In-Person', "Mon", "4:00 PM", "7:00 PM", 'First Game', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 3),
    (TRUE, 'Warhammer', 'Online', "Sat", "3:30 PM", "7:00 PM", 'Intermediate', '{"Tank": 1, "DPS" : 2, "Face" : 3, "Healer" : 4, "Support" : 5}', 3);

-- DATABASE Tests for Chatroom Feature


--
-- Table structure for table `mock_account`
--

DROP TABLE IF EXISTS `mock_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mock_account` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mock_account`
--

LOCK TABLES `mock_account` WRITE;
/*!40000 ALTER TABLE `mock_account` DISABLE KEYS */;
INSERT INTO `mock_account` VALUES (1,'GamemasterOne'),(2,'GamemasterTwo'),(3,'GamemasterThree'),(4,'Char1'),(5,'Char2'),(6,'Char3'),(7,'Char4'),(8,'Char5');
/*!40000 ALTER TABLE `mock_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mock_listing`
--

DROP TABLE IF EXISTS `mock_listing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mock_listing` (
  `listing_id` int NOT NULL AUTO_INCREMENT,
  `creator_id` int NOT NULL,
  `is_campaign_listing` tinyint NOT NULL,
  PRIMARY KEY (`listing_id`),
  KEY `creator_id_idx` (`creator_id`),
  CONSTRAINT `creator_id` FOREIGN KEY (`creator_id`) REFERENCES `mock_account` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mock_listing`
--

LOCK TABLES `mock_listing` WRITE;
/*!40000 ALTER TABLE `mock_listing` DISABLE KEYS */;
INSERT INTO `mock_listing` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,0),(5,5,0),(6,6,0),(7,7,0),(8,8,0);
/*!40000 ALTER TABLE `mock_listing` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `campaign_character_slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign_character_slots` (
  `campaign_listing_id` int NOT NULL,
  `character_listing_id` int NOT NULL,
  PRIMARY KEY (`campaign_listing_id`,`character_listing_id`),
  KEY `character_listing_id_idx` (`character_listing_id`),
  CONSTRAINT `campaign_listing_id` FOREIGN KEY (`campaign_listing_id`) REFERENCES `mock_listing` (`listing_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `character_listing_id` FOREIGN KEY (`character_listing_id`) REFERENCES `mock_listing` (`listing_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_character_slots`
--

LOCK TABLES `campaign_character_slots` WRITE;
/*!40000 ALTER TABLE `campaign_character_slots` DISABLE KEYS */;
INSERT INTO `campaign_character_slots` VALUES (1,4),(3,4),(2,5),(3,5),(2,6),(3,6),(3,7),(3,8);
/*!40000 ALTER TABLE `campaign_character_slots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatrooms`
--

DROP TABLE IF EXISTS `chatrooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatrooms` (
  `chatroom_id` int NOT NULL AUTO_INCREMENT,
  `campaign_id` int NOT NULL,
  UNIQUE KEY `chatroom_id_UNIQUE` (`chatroom_id`),
  KEY `campaign_id_idx` (`chatroom_id`,`campaign_id`),
  KEY `campaign_id_idx1` (`campaign_id`),
  CONSTRAINT `campaign_id` FOREIGN KEY (`campaign_id`) REFERENCES `mock_listing` (`listing_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatrooms`
--

LOCK TABLES `chatrooms` WRITE;
/*!40000 ALTER TABLE `chatrooms` DISABLE KEYS */;
INSERT INTO `chatrooms` VALUES (1,1),(2,2),(3,3);
/*!40000 ALTER TABLE `chatrooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_messages`
--

DROP TABLE IF EXISTS `chat_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_messages` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `chatroom_id` int NOT NULL,
  `user_id` int NOT NULL,
  `message` varchar(280) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`message_id`),
  KEY `chatroom_id_idx` (`chatroom_id`) /*!80000 INVISIBLE */,
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `chatroom_id` FOREIGN KEY (`chatroom_id`) REFERENCES `chatrooms` (`chatroom_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `mock_account` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_messages`
--

LOCK TABLES `chat_messages` WRITE;
/*!40000 ALTER TABLE `chat_messages` DISABLE KEYS */;
INSERT INTO `chat_messages` VALUES (18,1,1,'Hello WORLD','2024-11-25 01:18:01'),(19,1,1,'Hi','2024-11-25 01:18:10'),(20,1,4,'Hello','2024-11-25 01:18:15'),(21,1,4,'hru','2024-11-25 01:18:20'),(22,1,1,'fine','2024-11-25 01:18:23'),(23,1,4,'good','2024-11-25 01:18:25'),(24,1,1,'good talk','2024-11-25 01:18:23'),(25,2,2,'whose all here','2024-11-25 01:18:23'),(26,2,5,'me','2024-11-25 01:18:23'),(27,2,6,'me','2024-11-25 01:18:23'),(28,2,1,'me as well','2024-11-25 01:18:23'),(29,3,3,'I love board games','2024-11-25 01:18:20'),(30,3,4,'I love boats','2024-11-25 01:18:21'),(31,3,3,'I hate celery','2024-11-25 01:18:23'),(32,3,5,'I am neutral about opinions','2024-11-25 01:18:24'),(33,3,6,'I have a slight aversion to the color yellow','2024-11-25 01:18:25'),(34,3,7,'I am rather inclined to like sunny days','2024-11-25 01:18:26'),(35,3,8,'With all due respect, all of your opinions are trash.','2024-11-25 01:18:27');
/*!40000 ALTER TABLE `chat_messages` ENABLE KEYS */;
UNLOCK TABLES;