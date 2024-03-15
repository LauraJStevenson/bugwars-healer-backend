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
('original', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX X X X X X X X X X X X X XX\nX                           X\nXX XXXXX                   XX\nX  X a X            X a X   X\nXX X a               XXX   XX\nX    a X                    X\nXX X aaX       a      a    XX\nX  XXXXX                    X\nXX     a          XXXX     XX\nX                           X\nXX                         XX\nXXXXXX XXXX X X X XXXXX XX XX\nXX          f f f          XX\nX          f f f f          X\nXX          f f f          XX\nXX XX XXXXX X X X XXXX XXXXXX\nXX                         XX\nX                           X\nXX        XXXX       b     XX\nX                    XXXXX  X\nXX      b      b     Xbb X XX\nX                    X b    X\nXX     XXX             b X XX\nX     X b X          X b X  X\nXX                   XXXXX XX\nX                           X\nXX X X X X X X X X X X X X XX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('maze', E'XXXXXXXXXXXXXXXXXXXXXXX\nX X     X         X  aX\nX X X XXX XXX XXXXXXX X\nX X X   X X X   X     X\nX X XXX X X XXX X XXXXX\nX X X   X X X   X     X\nX X X XXX X X XXXXXXX X\nX   X X   X X   X     X\nXXXXX X XXX XXX X XXXXX\nX     X X     X X   X X\nX XXXXX X XXX X XXX X X\nX X     X X X X     X X\nX X XXXXX X X XXXXXXX X\nX X X     X X         X\nX X XXX X XXX XXXXXX XX\nX X X   X          X  X\nX X X X X XXXXXXXX XX X\nX X   X X X      X X  X\nX XXXXX X X XXXXXX X XX\nX X     X X        X  X\nX XXXXXXXXXXXXXXXXXXX X\nXt                    X\nXXXXXXXXXXXXXXXXXXXXXXX'),
('arena', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXXXXXXX   XXXXXXXXXXXXX\nXXXXXXXXXX         XXXXXXXXXX\nXXXXXXX               XXXXXXX\nXXXXXX                 XXXXXX\nXXXXX    1         2    XXXXX\nXXXX                     XXXX\nXXX    1     XXX     2    XXX\nXXX        XXXXXXX        XXX\nXXX  1     X     X     2  XXX\nXX            f            XX\nXX      XX         XX      XX\nXX      X           X      XX\nX      XX           XX      X\nX      XX f       f XX      X\nX      XX           XX      X\nXX      X           X      XX\nXX      XX         XX      XX\nXX            f            XX\nXXX  3     X     X     4  XXX\nXXX        XXXXXXX        XXX\nXXX    3     XXX     4    XXX\nXXXX                     XXXX\nXXXXX    3         4    XXXXX\nXXXXXX                 XXXXXX\nXXXXXXX               XXXXXXX\nXXXXXXXXXX         XXXXXXXXXX\nXXXXXXXXXXXXX   XXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),

