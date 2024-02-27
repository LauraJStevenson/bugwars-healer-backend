DROP TABLE IF EXISTS scripts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS map;

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

CREATE TABLE scripts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    name VARCHAR(50) NOT NULL,
    raw_code TEXT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT unique_user_script_name UNIQUE(user_id, name)
);

CREATE TABLE map (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    serialization VARCHAR (1000) NOT NULL
);

INSERT INTO users (username, firstname, lastname, password_hash, email, refresh_token, counter, activated)
VALUES
('TestLaura', 'Laura', 'Stevenson', '12345', 'laura@mail.com', NULL, 0, FALSE),
('TestAshley', 'Ashley', 'Mical', '45678', 'ashley@mail.com', NULL, 0, FALSE),
('TestYagmur', 'Yagmur', 'YagmurM', '123456', 'yagmur@yagmur.com', NULL, 0, FALSE);

INSERT INTO map (name, serialization)
VALUES
('basic', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX                           X\nX                           X\nX            a              X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX    b       f        c     X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX           d               X\nX                           X\nX                           X\nX                           X\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
