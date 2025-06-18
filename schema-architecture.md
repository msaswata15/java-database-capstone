# Smart Clinic Management System — Architecture Design

## 1️⃣ Architecture Summary

This Spring Boot application uses both **MVC** and **REST controllers** to serve different modules.  
The **Admin** and **Doctor Dashboards** use **Thymeleaf templates** for server-side rendering of HTML pages, while modules like **Appointments**, **Patient Dashboard**, and **Patient Records** expose **REST APIs** that return JSON.  
The application connects to **two databases**:  
- **MySQL** for structured data like patients, doctors, appointments, and admin records.  
- **MongoDB** for flexible, document-based data like prescriptions.  

All controllers delegate business logic to a **service layer**, which in turn communicates with **repositories** that abstract the database access. MySQL uses **JPA entities**, while MongoDB uses **document models**.

---

## 2️⃣ Numbered Flow of Data and Control

1. **User Interaction**  
   - Users access the system through Thymeleaf-based dashboards (Admin, Doctor) or REST API clients (mobile app, frontend modules).

2. **Controller Layer**  
   - Requests for HTML pages are handled by **Thymeleaf Controllers** that return `.html` templates.  
   - Requests for data APIs are handled by **REST Controllers** that return JSON responses.

3. **Service Layer**  
   - Controllers pass requests to the **service layer**, which contains the core business logic and validations.

4. **Repository Layer**  
   - Services call the **repository layer** to fetch or persist data.
   - There are separate repositories for MySQL (Spring Data JPA) and MongoDB (Spring Data MongoDB).

5. **Databases**  
   - Repositories connect to either **MySQL** (for structured data) or **MongoDB** (for flexible prescription data).

6. **Model Binding**  
   - Retrieved data is bound to Java model classes (`@Entity` for MySQL, `@Document` for MongoDB).

7. **Response to Client**  
   - In MVC, models are passed to Thymeleaf templates to render dynamic HTML.  
   - In REST, models or DTOs are serialized to JSON and sent back as API responses.

---

✅ **This architecture ensures a clean separation of concerns, scalability, and flexibility to serve both web pages and API clients efficiently.**

