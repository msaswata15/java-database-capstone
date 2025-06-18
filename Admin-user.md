1️⃣ Admin Login
markdown
Copy code
**Title:**  
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**  
1. Admin can enter username and password.  
2. System validates credentials.  
3. Admin is redirected to the dashboard upon successful login.

**Priority:** High

**Story Points:** 3

**Notes:**  
- Must follow secure authentication best practices.
2️⃣ Admin Logout
markdown
Copy code
**Title:**  
_As an admin, I want to log out of the portal, so that I can protect system access._

**Acceptance Criteria:**  
1. Logout option is visible on all pages.  
2. Session is terminated when logging out.  
3. User is redirected to login page.

**Priority:** High

**Story Points:** 1

**Notes:**  
- Ensure session cookies are cleared.
3️⃣ Add Doctor
markdown
Copy code
**Title:**  
_As an admin, I want to add doctors to the portal, so that they can provide medical services._

**Acceptance Criteria:**  
1. Admin can fill a form with doctor’s details.  
2. System validates input fields.  
3. Doctor profile is created and listed in the portal.

**Priority:** High

**Story Points:** 5

**Notes:**  
- Fields must include name, specialization, contact info.
4️⃣ Delete Doctor
markdown
Copy code
**Title:**  
_As an admin, I want to delete a doctor’s profile from the portal, so that outdated or incorrect profiles are removed._

**Acceptance Criteria:**  
1. Admin can select a doctor to delete.  
2. System asks for confirmation.  
3. Doctor profile is removed from the database.

**Priority:** Medium

**Story Points:** 3

**Notes:**  
- Ensure appointments linked to the doctor are handled properly.
5️⃣ Run Stored Procedure
markdown
Copy code
**Title:**  
_As an admin, I want to run a stored procedure in MySQL CLI to get the number of appointments per month, so that I can track usage statistics._

**Acceptance Criteria:**  
1. Stored procedure exists in the MySQL database.  
2. Admin can execute the procedure via CLI.  
3. System returns monthly appointment counts.

**Priority:** Medium

**Story Points:** 2

**Notes:**  
- Procedure must handle edge cases like no appointments.
