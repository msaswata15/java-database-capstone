✅ Section 1: Architecture Summary
The Smart Clinic Management System is designed as a three-tier web application using Spring Boot, which combines both Spring MVC with Thymeleaf for admin and doctor dashboards, and REST APIs for patient-facing and appointment modules.

The system uses:

Thymeleaf templates for rendering server-side HTML dashboards for Admins and Doctors.

RESTful endpoints for scalable, JSON-based communication for appointments, patient dashboards, and records.

A MySQL database for storing structured and relational data (e.g., patient info, doctor schedules, appointments, and admin details).

A MongoDB database for storing flexible, document-based data such as prescriptions.

Requests flow from the UI layer to the Controller layer, which routes them to the Service layer where business logic and validation are applied. Services interact with the Repository layer to perform database operations. Data is then mapped into models and returned to the client either as an HTML page (MVC) or as a JSON response (REST API).

This hybrid design makes the application scalable, maintainable, and cloud-ready, supporting both traditional web interactions and modern API consumption.

🔢 Section 2: Numbered Flow of Data and Control
1️⃣ User Request:
Users interact with the system through either:

Thymeleaf dashboards for Admin and Doctor interfaces, or

REST API clients for Appointments, Patient Dashboards, and Records.

2️⃣ Request Routing:
Requests are mapped to the appropriate Thymeleaf Controller or REST Controller based on the URL and HTTP method.

3️⃣ Controller Logic:
Controllers validate inputs, handle routing, and pass control to the Service Layer for further processing.

4️⃣ Business Logic Processing:
The Service Layer applies business rules (e.g., verifying doctor availability, checking patient details) and coordinates actions involving multiple entities.

5️⃣ Repository Access:
The Service Layer interacts with the Repository Layer:

MySQL Repositories (Spring Data JPA) for relational data.

MongoDB Repository (Spring Data MongoDB) for flexible, document-based data.

6️⃣ Database Operations:
Repositories abstract and execute database CRUD operations on MySQL tables and MongoDB collections.

7️⃣ Response Generation:

For MVC flows: Retrieved data populates model attributes rendered into Thymeleaf templates to produce dynamic HTML pages.

For REST flows: Data is serialized as JSON and returned to the client as a RESTful API response.

