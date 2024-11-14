CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  password VARCHAR(100),
  email VARCHAR(100)
);

INSERT INTO users (name,password,email) VALUES ('Berhans','BEESecret','bclar006@gmail.com');
INSERT INTO users (name) VALUES ('Bradley');
INSERT INTO users (name) VALUES ('Chris');
INSERT INTO users (name) VALUES ('Sebastian');
INSERT INTO users (name) VALUES ('Wyatt');
