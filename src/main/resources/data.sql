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
SELECT id, 'Script1', 'Test'
FROM users WHERE username = 'TestLaura';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script2', 'Test'
FROM users WHERE username = 'TestAshley';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script3', 'Test'
FROM users WHERE username = 'TestYagmur';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script4', 'Test'
FROM users WHERE username = 'TestKimlyn';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script5', 'Test'
FROM users WHERE username = 'TestViv';

INSERT INTO scripts (user_id, name, raw_code)
SELECT id, 'Script6', 'Test'
FROM users WHERE username = 'TestSam';

INSERT INTO map (name, serialization)
VALUES
('basic', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX                           X\nX                           X\nX            a              X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX    b       f        c     X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX                           X\nX           d               X\nX                           X\nX                           X\nX                           X\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('original', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX X X X X X X X X X X X X XX\nX                           X\nXX XXXXX                   XX\nX  X 1 X            X 1 X   X\nXX X 1               XXX   XX\nX    1 X                    X\nXX X 11X       1      1    XX\nX  XXXXX                    X\nXX     1          XXXX     XX\nX                           X\nXX                         XX\nXXXXXX XXXX X X X XXXXX XX XX\nXX          a a a          XX\nX          a a a a          X\nXX          a a a          XX\nXX XX XXXXX X X X XXXX XXXXXX\nXX                         XX\nX                           X\nXX        XXXX       0     XX\nX                    XXXXX  X\nXX      0      0     X00 X XX\nX                    X 0    X\nXX     XXX             0 X XX\nX     X 0 X          X 0 X  X\nXX                   XXXXX XX\nX                           X\nXX X X X X X X X X X X X X XX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
('maze', E'XXXXXXXXXXXXXXXXXXXXXXX\nX X     X         X   X\nX X X XXX XXX XXXXXXX X\nX X X   X X X   X     X\nX X XXX X X XXX X XXXXX\nX X X   X X X   X     X\nX X X XXX X X XXXXXXX X\nX   X X   X X   X     X\nXXXXX X XXX XXX X XXXXX\nX     X X     X X   X X\nX XXXXX X XXX X XXX X X\nX X     X X X X     X X\nX X XXXXX X X XXXXXXX X\nX X X     X X         X\nX X XXX X XXX XXXXXX XX\nX X X   X          X  X\nX X X X X XXXXXXXX XX X\nX X   X X X      X X  X\nX XXXXX X X XXXXXX X XX\nX X     X X        X  X\nX XXXXXXXXXXXXXXXXXXX X\nX                     X\nXXXXXXXXXXXXXXXXXXXXXXX'),
('arena', E'XXXXXXXXXXXXXXXXXXXXX\nXXXXXXXX     XXXXXXXX\nXXXXX           XXXXX\nXXXX   1     2   XXXX\nXXX  1         2  XXX\nXX  1           2  XX\nXX     XXXXXXX     XX\nXX                 XX\nX   XX    a    XX   X\nX   XX         XX   X\nX   XX a     a XX   X\nX   XX         XX   X\nX   XX    a    XX   X\nXX                 XX\nXX     XXXXXXX     XX\nXX  3           4  XX\nXXX  3         4  XXX\nXXXX   3     4   XXXX\nXXXXX           XXXXX\nXXXXXXXX     XXXXXXXX\nXXXXXXXXXXXXXXXXXXXXX');


COMMIT;