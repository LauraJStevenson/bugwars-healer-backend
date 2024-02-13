DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(15) NOT NULL UNIQUE,
    firstname VARCHAR(15) NOT NULL,
    lastname VARCHAR(15) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    refresh_token VARCHAR(255),
    counter INTEGER,
    activated BOOLEAN
);

INSERT INTO users (username, firstname, lastname, password_hash, email, refresh_token, counter, activated)
VALUES
('TestLaura', 'Laura', 'Stevenson', '12345', 'laura@mail.com', NULL, 0, FALSE),
('TestAshley', 'Ashley', 'Mical', '45678', 'ashley@mail.com', NULL, 0, FALSE);