# Users Microservice (Spring Boot)

Authentication and User Management Microservice providing JWT tokens, user registration, role management, and email verification.

- **Frontend Repository:** https://github.com/nissafh-create/films_dsi31_angular_frontend
- **Films Backend Repository:** https://github.com/nissafh-create/films_dsi31

---

## Features

- **JWT Authentication:** Generates and signs JWT tokens using HMAC256.
- **User Registration & Email Verification:** Sends verification codes via SMTP (Spring Mail) and validates tokens before activating user accounts.
- **Role-Based Access Control:** Manages roles (`ADMIN`, `USER`) attached to JWT token claims.
- **Spring Security 6+ & Spring Boot 3.2+** stateless security filter chain.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Security**
- **Spring Data JPA & Hibernate**
- **MySQL Connector**
- **Java JWT (Auth0)**
- **Spring Boot Starter Mail**
- **Lombok**
- **Maven**

---

## Getting Started

### Prerequisites

- JDK 21
- MySQL Server (running on port 3306)

### Configuration

Check `src/main/resources/application.properties`:
- **Server Port:** `8081` (context path: `/users`)
- **Database:** `jdbc:mysql://localhost:3306/films_soa_users`
- **Mail SMTP:** Configure your email and app password for sending verification emails:
  ```properties
  spring.mail.username=your-email@gmail.com
  spring.mail.password=your-app-password
  ```

### Running the Microservice

Using the Maven wrapper:

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

Or run `UsersMicroserviceApplication.java` directly from your IDE (IntelliJ IDEA / Eclipse).

---

## API Endpoints Overview

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/users/login` | Authenticate user and return JWT in the `Authorization` header |
| `POST` | `/users/register` | Register a new user and send verification email |
| `GET` | `/users/verifyEmail/{code}` | Verify code and activate user account |
| `GET` | `/users/all` | List all users (Secured) |
