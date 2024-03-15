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
           bytecode INTEGER[],
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
       ('arena', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXXXXXXXXXXXXX   XXXXXXXXXXXXX\nXXXXXXXXXX         XXXXXXXXXX\nXXXXXXX               XXXXXXX\nXXXXXX                 XXXXXX\nXXXXX    a         b    XXXXX\nXXXX                     XXXX\nXXX    a     XXX     b    XXX\nXXX        XXXXXXX        XXX\nXXX  a     X     X     b  XXX\nXX            f            XX\nXX      XX         XX      XX\nXX      X           X      XX\nX      XX           XX      X\nX      XX f       f XX      X\nX      XX           XX      X\nXX      X           X      XX\nXX      XX         XX      XX\nXX            f            XX\nXXX  c     X     X     d  XXX\nXXX        XXXXXXX        XXX\nXXX    c     XXX     d    XXX\nXXXX                     XXXX\nXXXXX    c         d    XXXXX\nXXXXXX                 XXXXXX\nXXXXXXX               XXXXXXX\nXXXXXXXXXX         XXXXXXXXXX\nXXXXXXXXXXXXX   XXXXXXXXXXXXX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
       ('maelstrom', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nXX      XXXX        XXX  XXXX\nXX        XXX      XXX    XXX\nX           X     XXX cc   XX\nX          XX     XX  c     X\nX          X      XX   XX   X\nX       X XX      XX  c XX  X\nX    d   XX        X c c Xc X\nX                  XX c XX  X\nX   X          X    XX XX   X\nXd  dX        XXX    XXX    X\nX     X                     X\nXd          XX              X\nX          XX  f            X\nX         X       X         X\nX            f  XX          X\nX              XX          cX\nX                     X     X\nX    XXX      XXX      Xc  cX\nX   XX XX      X        X   X\nX  XX d XX                  X\nX dX d d X        XX   c    X\nX  XX d  XX      XX X       X\nX   XX   XX      X          X\nX     d  XX     XX          X\nXX   dd XXX     X           X\nXXX    XXX      XXX        XX\nXXXX  XXX        XXXX      XX\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),
       ('labyrinth', E'XXXXXXXXXXXXXXXXXXXXXXXXXXXXX\nX                a         aX\nX                      a  a X\nX                      a  a X\nX                           X\nX   a                     a X\nX   XXXXXXXX    XXXXXXXXX   X\nX   X  X           X    X   X\nX   X XXXX XXXXXX XXXXX X   X\nX   X      X    X       X   X\nX   XXXXXX   XXXXXXXXXX X  aX\nX   X     XX      ff  X X   X\nX   X XXXX   XXX f  f   X   X\nX   X          X  ff  X X   X\nX   XXXXXXXXXXXXXXXXXXX X   X\nX   X          X  ff  X X   X\nX   X XXXX   XXX f  f   X   X\nX   X     XX      ff  X X   X\nXb  XXXXXX   XXXXXXXXXX X   X\nX   X      X    X       X   X\nX   X XXXX XXXXXX XXXXX X   X\nX   X  X           X    X   X\nX   XXXXXXXX    XXXXXXXXX   X\nX b                     b   X\nX                           X\nX b  b                      X\nX b  b                      X\nXb         b                X\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXX'),

       COMMIT;
