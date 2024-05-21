# Eligiendo un Futuro

Choosing a Future is a project designed to help people make informed decisions about their choice of educational center. This project combines a frontend application developed with Angular and two backends, one built with Spring Boot and the other with Express.

## Table of Contents

- [Description](#descripción)
- [Instalation](#instalación)
- [Usage](#uso)
- [License](#licencia)
- [Contact](#contacto)

## Description

Choosing a Future is an interactive tool that allows users to explore different options and scenarios for their future, providing information and resources that can aid in decision-making. The application is divided into three main parts: a frontend developed with Angular and two backends, one based on Spring Boot and the other on Express.

It is designed for searching educational centers where users can write comments, and the centers can respond and receive evaluations.
## Installation

### Requisitos Previos

Make sure you have the following tools installed:

- Node.js (versión 14 o superior)
- npm (versión 6 o superior)
- Java Development Kit (JDK) (versión 11 o superior)
- Apache Maven (versión 3.6.0 o superior)
- Docker y Docker Compose

### Instructions

1. **Clone the repository:**

    ```sh
    git clone https://github.com/tuusuario/eligiendo-un-futuro.git
    ```

2. **Frontend Installation (Angular):**

    Navigate to the frontend directory:

    ```sh
    cd eligiendo-un-futuro/EligiendoUnFuturoAng
    ```

    Install the dependencies:

    ```sh
    npm install
    ```

3. **Backend Installation (Spring Boot):**

    Navigate to the Spring Boot backend directory:

    ```sh
    cd ../escuelasrest
    ```

    Compile the project with Maven:

    ```sh
    mvn clean install
    ```

4. **Backend Installation (Express):**

    Navigate to the Express backend directory:

    ```sh
    cd ../EligiendoUnFuturoAng/backend
    ```

    Install the dependencies:

    ```sh
    npm install
    ```

## Usage

### Running with Docker

To start the services using Docker Compose, navigate to the docker directory:

```sh
cd docker
```

### Frontend (Angular)

To start the Angular development server, run:
```sh
npm start
```

### Backend (Spring Boot)

To start the Spring Boot application, run:

```sh
mvn spring-boot:run
```


### Backend (Express)

To start the Express server, run:

```sh
node .\api.js
```
## License-

Distributed under the GNU 2.0 License. See LICENSE for more information.

## Contacto

david87chdz@gmail.com

Project Link: https://github.com/david87chdz/eligiendo-un-futuro
