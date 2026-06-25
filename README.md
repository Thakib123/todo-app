# Todo REST API

![CI Build](https://github.com/Thakib123/todo-app/actions/workflows/ci.yml/badge.svg)

A secure RESTful Todo API built with Spring Boot, featuring JWT authentication.

## Features
- Full CRUD operations for todos
- JWT-based authentication (register & login)
- BCrypt password hashing
- Search and filter todos
- H2 in-memory database

## Tech Stack
- Java 21
- Spring Boot 3.5
- Spring Security
- Spring Data JPA
- H2 Database
- Maven

## Getting Started
1. Clone the repo
2. Run `mvn spring-boot:run`
3. The API starts on `http://localhost:8080`

## API Endpoints

### Auth
- `POST /api/auth/register` — create an account
- `POST /api/auth/login` — log in, returns a JWT token

### Todos (require Bearer token)
- `GET /api/todos` — list all todos
- `GET /api/todos/{id}` — get one todo
- `GET /api/todos/search?keyword=...` — search by title
- `GET /api/todos/status?completed=true` — filter by status
- `POST /api/todos` — create a todo
- `PUT /api/todos/{id}` — update a todo
- `DELETE /api/todos/{id}` — delete a todo

## Authentication
Include your token in the Authorization header:
```
Authorization: Bearer <your-token>
```
