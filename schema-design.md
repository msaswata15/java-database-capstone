# Smart Clinic Management System - Database Design

---

## ✅ MySQL Database Design

Below are the core tables needed for the clinic’s structured operational data.

---

### Table: patients

- id: INT, Primary Key, Auto Increment  
- first_name: VARCHAR(50), NOT NULL  
- last_name: VARCHAR(50), NOT NULL  
- email: VARCHAR(100), UNIQUE, NOT NULL  
- phone: VARCHAR(20), UNIQUE, NOT NULL  
- date_of_birth: DATE  
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

---

### Table: doctors

- id: INT, Primary Key, Auto Increment  
- first_name: VARCHAR(50), NOT NULL  
- last_name: VARCHAR(50), NOT NULL  
- specialization: VARCHAR(100), NOT NULL  
- email: VARCHAR(100), UNIQUE, NOT NULL  
- phone: VARCHAR(20), UNIQUE, NOT NULL  
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

---

### Table: appointments

- id: INT, Primary Key, Auto Increment  
- doctor_id: INT, Foreign Key → doctors(id), NOT NULL  
- patient_id: INT, Foreign Key → patients(id), NOT NULL  
- appointment_time: DATETIME, NOT NULL  
- status: TINYINT (0 = Scheduled, 1 = Completed, 2 = Cancelled)  
- created_at: DATETIME, DEFAULT CURRENT_TIMESTAMP

---

### Table: admin

- id: INT, Primary Key, Auto Increment  
- username: VARCHAR(50), UNIQUE, NOT NULL  
- password_hash: VARCHAR(255), NOT NULL  
- role: VARCHAR(20) DEFAULT 'admin'

---

### Table: payments _(Optional for future expansion)_

- id: INT, Primary Key, Auto Increment  
- patient_id: INT, Foreign Key → patients(id), NOT NULL  
- amount: DECIMAL(10, 2), NOT NULL  
- payment_date: DATETIME, NOT NULL  
- method: VARCHAR(50) (e.g., Credit Card, Cash)  
- status: VARCHAR(20) (e.g., Paid, Pending, Failed)

---

**Notes:**  
- Foreign keys ensure appointments link to valid patients & doctors.  
- Emails and phones should be unique for clear communication.  
- Consider cascading delete or restrict policies depending on business rules (e.g., you may not want to delete a patient if they have active appointments).

---

## ✅ MongoDB Collection Design

Below is a flexible document design for storing prescriptions and doctor notes, which may vary in structure.

---

### Collection: prescriptions

```json
{
  "_id": "ObjectId('64abc123456')",
  "patientId": 5,
  "doctorId": 2,
  "appointmentId": 20,
  "medications": [
    {
      "name": "Amoxicillin",
      "dosage": "500mg",
      "instructions": "Take twice a day after meals"
    },
    {
      "name": "Paracetamol",
      "dosage": "650mg",
      "instructions": "Take as needed for fever"
    }
  ],
  "doctorNotes": "Patient should return for follow-up in 2 weeks.",
  "createdAt": "2025-06-18T12:00:00Z",
  "pharmacy": {
    "name": "Health Pharmacy",
    "location": "Downtown Clinic Branch"
  },
  "refillsAllowed": 1
}
