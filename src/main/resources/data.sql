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
('basic', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX                           X\nX                           X\nX            a              X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX    b       f        c     X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX           d               X\nX                           X\nX                           X\nX                           X\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('original', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX X X X X X X X X X X X X XX\nX                           X\nXX XXXXX                   XX\nX  X 1 X            X 1 X   X\nXX X 1               XXX   XX\nX    1 X                    X\nXX X 11X       1      1    XX\nX  XXXXX                    X\nXX     1          XXXX     XX\nX                           X\nXX                         XX\nXXXXXX XXXX X X X XXXXX XX XX\nXX          a a a          XX\nX          a a a a          X\nXX          a a a          XX\nXX XX XXXXX X X X XXXX XXXXXX\nXX                         XX\nX                           X\nXX        XXXX       0     XX\nX                    XXXXX  X\nXX      0      0     X00 X XX\nX                    X 0    X\nXX     XXX             0 X XX\nX     X 0 X          X 0 X  X\nXX                   XXXXX XX\nX                           X\nXX X X X X X X X X X X X X XX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('maze', E'XXXXXXXXXXXXXXXXXXXXXXX\nX X     X         X   X\nX X X XXX XXX XXXXXXX X\nX X X   X X X   X     X\nX X XXX X X XXX X XXXXX\nX X X   X X X   X     X\nX X X XXX X X XXXXXXX X\nX   X X   X X   X     X\nXXXXX X XXX XXX X XXXXX\nX     X X     X X   X X\nX XXXXX X XXX X XXX X X\nX X     X X X X     X X\nX X XXXXX X X XXXXXXX X\nX X X     X X         X\nX X XXX X XXX XXXXXX XX\nX X X   X          X  X\nX X X X X XXXXXXXX XX X\nX X   X X X      X X  X\nX XXXXX X X XXXXXX X XX\nX X     X X        X  X\nX XXXXXXXXXXXXXXXXXXX X\nX                     X\nXXXXXXXXXXXXXXXXXXXXXXX')

