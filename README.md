# Library Management System API

This project is a Spring Boot application that implements a Library Management System API. It allows librarians to
manage books, patrons, and borrowing records efficiently.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Running the Application](#running-the-application)
   - [Providing Configuration](#providing-configuration)
3. [Interacting with API Endpoints](#interacting-with-api-endpoints)
   - [Books Endpoints](#books-endpoints)
   - [Patrons Endpoints](#patrons-endpoints)
   - [Borrowing Endpoints](#borrowing-endpoints)
   - [Login Endpoint](#login-endpoint)

## Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK) version 8 or higher
- Maven

## Running the Application

To run the Library Management System API:

1. Clone this repository to your local machine.
2. Navigate to the project directory in your terminal or command prompt.
3. Build the project using Maven:

   ```
   mvn clean install
   ```

4. Run the application using the Spring Boot Maven plugin:

   ```
   mvn spring-boot:run
   ```

5. Once the application is running, it will be accessible at `http://localhost:8080`.

### Providing Configuration

The `application.properties` file contains configurations required for running the application. However, to ensure
security, sensitive information such as passwords, API keys, or private URLs has been replaced in the file, with
placeholders such as YourUsername for usernames, YourPassword for passwords, and so on.

To provide your own configurations and set up the application locally:

1. Navigate to `application.properties` file in the `src/main/resources` directory of the project.
2. Replace all the placeholders with the necessary configurations.

## Interacting with API Endpoints

You can interact with the API endpoints using tools like cURL, Postman, or any web browser. Here are the available
endpoints:

### Books Endpoints:

#### 1. GET /api/books

- **Description**: Retrieve a list of all books.
- **Request**:
  - Method: GET
  - Endpoint: `/api/books`
  - Headers:
    - Authorization: Bearer Token
  - Query Parameters:
    - `p` (optional): Indicates the page number. If not provided, default is 0
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Books retrieved successfully",
      "body": [
        {
          "bookId": "db74aca7-e71b-409f-ba10-7e6910d60aaf",
          "title": "example book",
          "author": "example author",
          "publicationYear": 2024,
          "isbn": "888-0-306-40615-7",
          "price": 125.5,
          "addedAt": "2024-05-12T13:01:25.395+00:00"
        },
        {
          "bookId": "db74aca7-e71b-409f-ba10-7e6910d60aaf",
          "title": "second book",
          "author": "second author",
          "publicationYear": 2024,
          "isbn": "736-4-582-87946-5",
          "price": 125.5,
          "addedAt": "2024-05-12T13:02:34.250+00:00"
        }
      ]
    }
    ```

#### 2. GET /api/books/{id}

- **Description**: Retrieve details of a specific book by ID.
- **Request**:
  - Method: GET
  - Endpoint: `/api/books/{id}`
  - Headers:
    - Authorization: Bearer Token
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Book retrieved successfully",
      "body": {
        "bookId": "db74aca7-e71b-409f-ba10-7e6910d60aaf",
        "title": "example book",
        "author": "example author",
        "publicationYear": 2024,
        "isbn": "888-0-306-40615-7",
        "price": 125.5,
        "addedAt": "2024-05-12T13:01:25.395+00:00"
      }
    }
    ```

#### 3. POST /api/books

- **Description**: Add a new book to the library.
- **Request**:
  - Method: POST
  - Endpoint: `/api/books`
  - Headers:
    - Content-Type: application/json
    - Authorization: Bearer Token
  - Body:
    ```json
    {
      "title": "example book",
      "author": "example author",
      "publicationYear": 2024,
      "isbn": "888-0-306-40615-7",
      "price": 125.5
    }
    ```
- **Response**:
  - Status: 201 Created
  - Body:
    ```json
    {
      "status": "success",
      "message": "Book created successfully",
      "body": {
        "bookId": "db74aca7-e71b-409f-ba10-7e6910d60aaf",
        "title": "example book",
        "author": "example author",
        "publicationYear": 2024,
        "isbn": "888-0-306-40615-7",
        "price": 125.5,
        "addedAt": "2024-05-12T13:01:25.395+00:00"
      }
    }
    ```

#### 4. PUT /api/books/{id}

- **Description**: Update an existing book's information.
- **Request**:
  - Method: PUT
  - Endpoint: `/api/books/{id}`
  - Headers:
    - Content-Type: application/json
    - Authorization: Bearer Token
  - Body:
    ```json
    {
      "title": "edited",
      "author": "example author",
      "publicationYear": 2024,
      "isbn": "888-0-306-40615-7",
      "price": 125.5
    }
    ```
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Book updated successfully",
      "body": {
        "bookId": "db74aca7-e71b-409f-ba10-7e6910d60aaf",
        "title": "edited",
        "author": "example author",
        "publicationYear": 2024,
        "isbn": "888-0-306-40615-7",
        "price": 125.5,
        "addedAt": "2024-05-12T13:01:25.395+00:00"
      }
    }
    ```

#### 5. DELETE /api/books/{id}

- **Description**: Remove a book from the library.
- **Request**:
  - Method: DELETE
  - Endpoint: `/api/books/{id}`
  - Headers:
    - Authorization: Bearer Token
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Book deleted successfully",
      "body": "No data"
    }
    ```

### Patrons Endpoints:

#### 1. GET /api/patrons

- **Description**: Retrieve a list of all patrons.
- **Request**:
  - Method: GET
  - Endpoint: `/api/patrons`
  - Headers:
    - Authorization: Bearer Token
  - Query Parameters:
    - `p` (optional): Indicates the page number. If not provided, default is 0
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Patrons retrieved successfully",
      "body": [
        {
          "patronId": "089a3da4-b449-4619-8401-dd00dd9b1920",
          "name": "tester",
          "username": "test",
          "email": "test@mail.com",
          "phoneNumber": "01117332086",
          "address": null,
          "dateOfBirth": null
        },
        {
          "patronId": "dbed70a0-3005-4b00-bd08-88ded5f4fa45",
          "name": "example",
          "username": "example123",
          "email": "example@mail.com",
          "phoneNumber": "01234567891",
          "address": "Cairo, Egypt",
          "dateOfBirth": "1999-01-01"
        }
      ]
    }
    ```

#### 2. GET /api/patrons/{id}

- **Description**: Retrieve details of a specific patron by ID.
- **Request**:
  - Method: GET
  - Endpoint: `/api/patrons/{id}`
  - Headers:
    - Authorization: Bearer Token
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Patron retrieved successfully",
      "body": {
        "patronId": "dbed70a0-3005-4b00-bd08-88ded5f4fa45",
        "name": "example",
        "username": "example123",
        "email": "example@mail.com",
        "phoneNumber": "01234567891",
        "address": "Cairo, Egypt",
        "dateOfBirth": "1999-01-01"
      }
    }
    ```

#### 3. POST /api/patrons

- **Description**: Register a new patron to the system.
- **Request**:
  - Method: POST
  - Endpoint: `/api/patrons`
  - Headers:
    - Content-Type: application/json
  - Body:
    ```json
    {
      "name": "example",
      "username": "example123",
      "email": "example@mail.com",
      "password": "example123@E",
      "phoneNumber": "01234567891"
    }
    ```
- **Response**:
  - Status: 201 Created
  - Body:
    ```json
    {
      "status": "success",
      "message": "Patron Registered successfully",
      "body": "No data"
    }
    ```

#### 4. PUT /api/patrons/{id}

- **Description**: Update an existing patron's information.
- **Request**:
  - Method: PUT
  - Endpoint: `/api/patrons/{id}`
  - Headers:
    - Content-Type: application/json
    - Authorization: Bearer Token
  - Body:
    ```json
    {
      "name": "newName",
      "phoneNumber": "01013381867",
      "address": "Cairo, Egypt",
      "dateOfBirth": "2002-07-21"
    }
    ```
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Patron updated successfully",
      "body": {
        "patronId": "dbed70a0-3005-4b00-bd08-88ded5f4fa45",
        "name": "newName",
        "username": "example123",
        "email": "example@mail.com",
        "phoneNumber": "01013381867",
        "address": "Cairo, Egypt",
        "dateOfBirth": "2002-07-21"
      }
    }
    ```

#### 5. DELETE /api/patrons/{id}

- **Description**: Remove a patron from the system.
- **Request**:
  - Method: DELETE
  - Endpoint: `/api/patrons/{id}`
  - Headers:
    - Authorization: Bearer Token
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Patron deleted successfully",
      "body": "No data"
    }
    ```

### Borrowing Endpoints:

#### 1. POST /api/borrow/{bookId}/patron/{patronId}

- **Description**: Allow a patron to borrow a book.
- **Request**:
  - Method: POST
  - Endpoint: `/api/borrow/{bookId}/patron/{patronId}`
  - Headers:
    - Authorization: Bearer Token
  - Body: None
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Book borrowed successfully"
    }
    ```

#### 2. PUT /api/return/{bookId}/patron/{patronId}

- **Description**: Record the return of a borrowed book by a patron.
- **Request**:
  - Method: PUT
  - Endpoint: `/api/return/{bookId}/patron/{patronId}`
  - Headers:
    - Authorization: Bearer Token
  - Body: None
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Book returned successfully"
    }
    ```

### Login Endpoint:

- **Description**: Authenticate a patron and generate a JWT token.
- **Request**:
  - Method: POST
  - Endpoint: `/api/login`
  - Headers:
    - Content-Type: application/json
  - Body:
    ```json
    {
      "usernameOrEmail": "example123",
      "password": "example123@E"
    }
    ```
- **Response**:
  - Status: 200 OK
  - Body:
    ```json
    {
      "status": "success",
      "message": "Login successful",
      "body": {
        "username": "example123",
        "email": "example@mail.com",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJUb2tlbiIsImlzcyI6Ik1haWRzY2MiLCJpZCI6ImI0Mjc2ZjJhLTZlMmUtNDliZC1hZTUzLTY3MDZhNjQ0ZDdlYyIsInVzZXJuYW1lIjoiZXhhbXBsZTEyMyIsImlhdCI6MTcxNTUyMTg5OCwiZXhwIjoxNzE1NTIxODk4fQ.zEw9SykMSTHEg7cPco5hUjXm_fuiG8VCMX-lfGuTZgRD9YnYtCPdUe6-XneAjQcxMD831Iovk1A1JsN7cKv2zA"
      }
    }
    ```
