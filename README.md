# 📝 Todo API (Spring Boot + JWT)

This is a simple and secure **Todo REST API** built using Spring Boot.
The application allows users to register, authenticate, and manage their own todo items.

---

## 🚀 Features

* 🔐 User registration and login
* 🔑 JWT (JSON Web Token) authentication
* 📋 Create, read, update, and delete todos (CRUD)
* 👤 User-specific data (each user can only access their own todos)
* 🛡️ Secured endpoints with Spring Security
* 🔍 Filter todos by completion status

---

## 🛠️ Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* PostgreSQL
* Maven

---

## ⚙️ Setup & Run

1. Create a PostgreSQL database:

```bash
todo_db
```

2. Configure your database in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_db
spring.datasource.username=postgres
spring.datasource.password=1234
```

3. Run the application from your IDE (IntelliJ, VS Code, etc.)

---

## 📌 API Endpoints

### 🔐 Auth

* `POST /auth/register` → Register a new user
* `POST /auth/login` → Login and receive JWT token

---

### 📋 Todo

* `GET /api/todos` → Get all todos
* `GET /api/todos/get/{id}` → Get todo by ID
* `POST /api/todos` → Create a new todo
* `PUT /api/todos/update/{id}` → Update a todo
* `DELETE /api/todos/delete/{id}` → Delete a todo
* `GET /api/todos/status/{completed}` → Filter todos by completion status

---

## 🔒 Authorization

All `/api/**` endpoints require a valid JWT token.

Add the following header to your requests:

```http
Authorization: Bearer <your_token>
```

---

## 📷 Example Request

```json
{
  "title": "Learn Spring Boot",
  "description": "Build a REST API project",
  "completed": false
}
```

---

## 🧪 Testing

You can test the API using:

* Postman
* Insomnia

Make sure to:

1. Register a user
2. Login to get a token
3. Use the token in Authorization header

---

## 📄 Notes

* This project is built for learning purposes.
* Make sure PostgreSQL is running before starting the application.
* `spring.jpa.hibernate.ddl-auto=update` is recommended for development.

---

## ⭐ Contribution

Feel free to fork this repository and improve it.
