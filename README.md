# Smart Clinic Management System

A backend project for managing clinics, built with **Spring Boot**, **Java 17**, **Maven**, **MySQL**, **MongoDB**, and **Docker**.  
It supports secure management of doctors, patients, appointments, and prescriptions, with both REST APIs and Thymeleaf dashboards.

---

## Features

- Admin, Doctor, and Patient roles with JWT authentication
- RESTful APIs for appointments, patients, doctors, and prescriptions
- Thymeleaf dashboards for Admin and Doctor
- MySQL for structured data (patients, doctors, appointments, admin)
- MongoDB for prescription storage
- Docker support for easy deployment

---

## Project Structure

```
app/
├── Dockerfile
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/project/back_end/
│   │   │   ├── config/
│   │   │   ├── controllers/
│   │   │   ├── models/
│   │   │   ├── repo/
│   │   │   ├── services/
│   │   │   └── BackEndApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
└── ...
```

---

## Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Data JPA (MySQL)
- Spring Data MongoDB
- Thymeleaf
- JWT
- Docker

---

## Database Design

- MySQL: patients, doctors, appointments, admin, payments
- MongoDB: prescriptions collection

---

## Architecture

- MVC + REST: Thymeleaf for dashboards, REST APIs for integration
- Service Layer: business logic and validation
- Repository Layer: JPA for MySQL, MongoRepository for MongoDB

---

## User Stories

See `user_stories.md` for detailed requirements for Admin, Doctor, and Patient roles.

---

## Docker Usage

**Build the image:**
```sh
docker build -t java-database-capstone .
```

**Run the container:**
```sh
docker run -p 8080:8080 java-database-capstone
```

---

## Local Development

**Build and run:**
```sh
mvn clean package
java -jar target/back-end-0.0.1-SNAPSHOT.jar
```

**Configuration:**  
Edit `src/main/resources/application.properties` for your database and JWT settings:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cms
spring.datasource.username=your_mysql_user
spring.datasource.password=your_mysql_password

spring.data.mongodb.uri=mongodb://localhost:27017/prescriptions

jwt.secret=your_jwt_secret
```

---

## API & UI

- API Endpoints: `/api/doctors`, `/api/patients`, `/api/appointments`, `/api/prescriptions`, etc.
- Dashboards: `/admin/dashboard`, `/doctor/dashboard`
- Static Frontend: `src/main/resources/static/index.html`

---

## Testing

Run all tests:
```sh
mvn test
```

---

## License

MIT License