BEGIN;

DROP TABLE IF EXISTS scripts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS map;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

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

--Insert test user accounts--
INSERT INTO users (username, firstname, lastname, password_hash, email, refresh_token, counter, activated)
VALUES
('TestLaura', 'Laura', 'LauraS', crypt('Test1234', gen_salt('bf')), 'laura@mail.com', NULL, 0, FALSE),
('TestAshley', 'Ashley', 'AshleyM', crypt('Test1234', gen_salt('bf')), 'ashley@mail.com', NULL, 0, FALSE),
('TestYagmur', 'Yagmur', 'YagmurM', crypt('Test1234', gen_salt('bf')), 'yagmur@yagmur.com', NULL, 0, FALSE),
('TestKimlyn', 'Kimlyn', 'KimlynD', crypt('Test1234', gen_salt('bf')), 'kimlyn@mail.com', NULL, 0, FALSE),
('TestViv', 'Viv', 'VivV', crypt('Test1234', gen_salt('bf')), 'viv@mail.com', NULL, 0, FALSE),
('TestSam', 'Sam', 'SamB', crypt('Test1234', gen_salt('bf')), 'sam@mail.com', NULL, 0, FALSE);

--Insert test scripts for each test user--
INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script1', ':Start\nifFood FoodFound\nifEmpty MoveForward\nifEnemy Attack\nnoop\n\n:FoodFound\nrotr\ngoto Start\n\n:MoveForward\nmov\ngoto Start\n\n:Attack\natt\ngoto Start'
FROM users WHERE username = 'TestLaura';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script2', ':Start\nifFood FoodFound\nifEmpty MoveForward\nifEnemy Attack\nnoop\n\n:FoodFound\nrotr\ngoto Start\n\n:MoveForward\nmov\ngoto Start\n\n:Attack\natt\ngoto Start'
FROM users WHERE username = 'TestAshley';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script3', ':Start\nifFood FoodFound\nifEmpty MoveForward\nifEnemy Attack\nnoop\n\n:FoodFound\nrotr\ngoto Start\n\n:MoveForward\nmov\ngoto Start\n\n:Attack\natt\ngoto Start'
FROM users WHERE username = 'TestYagmur';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script4', ':Start\nifFood FoodFound\nifEmpty MoveForward\nifEnemy Attack\nnoop\n\n:FoodFound\nrotr\ngoto Start\n\n:MoveForward\nmov\ngoto Start\n\n:Attack\natt\ngoto Start'
FROM users WHERE username = 'TestKimlyn';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script5', ':Start\nifFood FoodFound\nifEmpty MoveForward\nifEnemy Attack\nnoop\n\n:FoodFound\nrotr\ngoto Start\n\n:MoveForward\nmov\ngoto Start\n\n:Attack\natt\ngoto Start'
FROM users WHERE username = 'TestViv';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script6', ':Start\nifFood FoodFound\nifEmpty MoveForward\nifEnemy Attack\nnoop\n\n:FoodFound\nrotr\ngoto Start\n\n:MoveForward\nmov\ngoto Start\n\n:Attack\natt\ngoto Start'
FROM users WHERE username = 'TestSam';


INSERT INTO map (name, serialization)
VALUES
('basic', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX                           X\nX                           X\nX            a              X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX    b       f        c     X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX           d               X\nX                           X\nX                           X\nX                           X\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('original', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX X X X X X X X X X X X X XX\nX                           X\nXX XXXXX                   XX\nX  X a X            X a X   X\nXX X a               XXX   XX\nX    a X                    X\nXX X aaX       a      a    XX\nX  XXXXX                    X\nXX     a          XXXX     XX\nX                           X\nXX                         XX\nXXXXXX XXXX X X X XXXXX XX XX\nXX          f f f          XX\nX          f f f f          X\nXX          f f f          XX\nXX XX XXXXX X X X XXXX XXXXXX\nXX                         XX\nX                           X\nXX        XXXX       b     XX\nX                    XXXXX  X\nXX      b      b     Xbb X XX\nX                    X b    X\nXX     XXX             b X XX\nX     X b X          X b X  X\nXX                   XXXXX XX\nX                           X\nXX X X X X X X X X X X X X XX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('maze', E'XXXXXXXXXXXXXXXXXXXXXXX\nX X     X         X  aX\nX X X XXX XXX XXXXXXX X\nX X X   X X X   X     X\nX X XXX X X XXX X XXXXX\nX X X   X X X   X     X\nX X X XXX X X XXXXXXX X\nX   X X   X X   X     X\nXXXXX X XXX XXX X XXXXX\nX     X X     X X   X X\nX XXXXX X XXX X XXX X X\nX X     X X X X     X X\nX X XXXXX X X XXXXXXX X\nX X X     X X         X\nX X XXX X XXX XXXXXX XX\nX X X   X          X  X\nX X X X X XXXXXXXX XX X\nX X   X X X      X X  X\nX XXXXX X X XXXXXX X XX\nX X     X X        X  X\nX XXXXXXXXXXXXXXXXXXX X\nXt                    X\nXXXXXXXXXXXXXXXXXXXXXXX'),
('arena', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXXXXXXX   XXXXXXXXXXXXX\nXXXXXXXXXX         XXXXXXXXXX\nXXXXXXX               XXXXXXX\nXXXXXX                 XXXXXX\nXXXXX    1         2    XXXXX\nXXXX                     XXXX\nXXX    1     XXX     2    XXX\nXXX        XXXXXXX        XXX\nXXX  1     X     X     2  XXX\nXX            f            XX\nXX      XX         XX      XX\nXX      X           X      XX\nX      XX           XX      X\nX      XX f       f XX      X\nX      XX           XX      X\nXX      X           X      XX\nXX      XX         XX      XX\nXX            f            XX\nXXX  3     X     X     4  XXX\nXXX        XXXXXXX        XXX\nXXX    3     XXX     4    XXX\nXXXX                     XXXX\nXXXXX    3         4    XXXXX\nXXXXXX                 XXXXXX\nXXXXXXX               XXXXXXX\nXXXXXXXXXX         XXXXXXXXXX\nXXXXXXXXXXXXX   XXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),

COMMIT;