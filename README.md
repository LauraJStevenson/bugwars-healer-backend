# Bug Wars

Bug Wars is a Java-based programming competition where players select or program intelligent creatures to navigate mazes, overcome obstacles, and compete for control of maps.


## Table of contents

- [Overview](#overview)
  - [Screenshots](#screenshots)
  - [Links](#links)
- [Our process](#our-process)
  - [Built with](#built-with)
  - [Continued development](#continued-development)
- [Contributors](#contributors)

## Overview

### Screenshots

![ScreenCapHomePage](https://github.com/LauraJStevenson/bugwars-healer-backend/assets/117865610/29c368d1-b207-4779-9033-d1e8f632dbab)

### Links

- Production Environment URL: [Bug Wars Front End](https://bugwars-healer-frontend.onrender.com)
- Front End Repo: [Bug Wars FE](https://github.com/LauraJStevenson/bugwars-healer-frontend)
- Back End Repo: [Bug Wars BE](https://github.com/LauraJStevenson/bugwars-healer-backend)

### Our Process

### Built with

* HTML/CSS/JavaScript
* Java
* PostgreSQL
* Vue.js
* Docker
* SpringBoot

### Project Setup

In order to run this project on your local machine, there are a few steps you need to follow:
1. Clone the repository for both the FrontEnd and BackEnd to your machine.
2. Use pgAdmin to create a PostgreSQL database called bug_wars.
3. Create a file named `.env` in the Root Directory of the BackEnd portion of the project. Populate it with the following values:

    `DB_HOST=localhost`

    `DB_PORT=5432`

    `DB_NAME=bug_wars`

    `DB_USER=postgres`

    `DB_PASSWORD=postgres1`

    `DB_DDL=create-drop`

    `INIT_MODE=ALWAYS`

    `JWT_TOKEN_GENERATED=contact Laura for this`

   Please note, the values after the <=> may be different depending on your setup of pgAdmin. Use your own Username and Password.

4. Run the model application: `BugWarsHealerBackendApplication`. Your service should be live on `localhost:8080`.

4. Create a file named `.env` in the Root Directory of the FrontEnd portion of the project. Populate it with the following value:

    `VITE_REMOTE_API = http://localhost:8080/api/v1`

   Please note, if your machine is configured differently, the port number may need to be changed.

6. Run the command `npm install` in the terminal for the FrontEnd of the project.
7. Run the command `npm run dev` for a developer preview of the project. 

### Continued development

- Players now have the ability to create their own scripts for creatures.
- Development Blog on live site.
- More robust testing for both Server & Client portions of our Application.
- We now have a Game engine to run the game.
- We have Compiler logic to parse bug code into actions (bytecode).
- Design maps for the bugs to compete on.
    If you would like to add maps to the project, please use the following key:
    X = Wall
    a, b, c, d = Bug4
    f = Food
    empty = Blank Space
    


## Contributors

- [Laura's GitHub](https://github.com/LauraJStevenson)
- [Ashley's GitHub](https://github.com/micamash)
- [Kimlyn's GitHub](https://github.com/klyndelara)
- [Samanatha's GitHub](https://github.com/sbutterfield5)
- [Viv's GitHub](https://github.com/Viv-Valentin)
- [Yagmur's GitHub](https://github.com/yagmurmuslu)
