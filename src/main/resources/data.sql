CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(15) NOT NULL,
    firstname VARCHAR(15) NOT NULL,
    lastname VARCHAR(15) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    counter INTEGER,
    activated BOOLEAN
);

INSERT INTO users (username, firstname, lastname, password_hash, email)
VALUES
('TestLaura', 'Laura', 'Stevenson', '12345', 'laura@mail.com'),
('TestAshley', 'Ashley', 'Mical', '45678', 'ashley@mail.com');