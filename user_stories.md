# User Story Template

**Title:**
_As a [user role], I want [feature/goal], so that [reason]._

**Acceptance Criteria:**
1. [Criteria 1]
2. [Criteria 2]
3. [Criteria 3]

**Priority:** [High/Medium/Low]
**Story Points:** [Estimated Effort in Points]
**Notes:**
- [Additional information or edge cases]

✅ Admin User Stories
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

✅ Patient User Stories
1️⃣ View Doctors Without Login
markdown
Copy code
**Title:**  
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**  
1. List of available doctors is visible on the homepage.  
2. Doctor profiles include name, specialization, and contact info.  
3. No login is required to view this list.

**Priority:** High

**Story Points:** 3

**Notes:**  
- Profiles must not expose private information.
2️⃣ Patient Sign Up
markdown
Copy code
**Title:**  
_As a patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria:**  
1. Registration form includes email, password, and basic info.  
2. System validates input and shows errors if any.  
3. Account is created, and patient is redirected to login.

**Priority:** High

**Story Points:** 3

**Notes:**  
- Password should follow security requirements.
3️⃣ Patient Login
markdown
Copy code
**Title:**  
_As a patient, I want to log into the portal, so that I can manage my bookings._

**Acceptance Criteria:**  
1. Patient enters email and password to login.  
2. System authenticates credentials.  
3. Patient is redirected to dashboard on success.

**Priority:** High

**Story Points:** 2

**Notes:**  
- Session must expire after inactivity.
4️⃣ Patient Logout
markdown
Copy code
**Title:**  
_As a patient, I want to log out of the portal, so that I can secure my account._

**Acceptance Criteria:**  
1. Logout option is accessible on all pages.  
2. Session is cleared on logout.  
3. Patient is redirected to homepage or login page.

**Priority:** High

**Story Points:** 1

**Notes:**  
- Clear cookies and tokens.
5️⃣ Book Appointment
markdown
Copy code
**Title:**  
_As a patient, I want to log in and book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**  
1. Patient selects a doctor and an available time slot.  
2. System checks availability and confirms booking.  
3. Appointment is saved and patient receives confirmation.

**Priority:** High

**Story Points:** 5

**Notes:**  
- Prevent double booking for same time slot.
6️⃣ View Upcoming Appointments
markdown
Copy code
**Title:**  
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**  
1. Patient sees a list of future appointments.  
2. Each entry shows date, time, doctor’s name, and location.  
3. Patient can cancel or reschedule if needed.

**Priority:** High

**Story Points:** 3

**Notes:**  
- Include pagination if many appointments.

  ✅ Doctor User Stories
1️⃣ Doctor Login
markdown
Copy code
**Title:**  
_As a doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**  
1. Doctor enters username and password to login.  
2. System authenticates credentials.  
3. Doctor is redirected to the dashboard on success.

**Priority:** High

**Story Points:** 2

**Notes:**  
- Session must time out for security.
2️⃣ Doctor Logout
markdown
Copy code
**Title:**  
_As a doctor, I want to log out of the portal, so that I can protect my data._

**Acceptance Criteria:**  
1. Logout button is accessible on all pages.  
2. Session data is cleared on logout.  
3. Doctor is redirected to the login page.

**Priority:** High

**Story Points:** 1

**Notes:**  
- Ensure session cookies are cleared.
3️⃣ View Appointment Calendar
markdown
Copy code
**Title:**  
_As a doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**  
1. Calendar displays all confirmed appointments.  
2. Each appointment shows patient name, time, and reason.  
3. Doctor can switch between daily, weekly, and monthly views.

**Priority:** High

**Story Points:** 4

**Notes:**  
- Should sync with backend data in real-time.
4️⃣ Mark Unavailability
markdown
Copy code
**Title:**  
_As a doctor, I want to mark my unavailability, so that patients can only book available slots._

**Acceptance Criteria:**  
1. Doctor selects dates and times to block.  
2. Blocked slots are updated in the booking system.  
3. Patients cannot see or book blocked slots.

**Priority:** High

**Story Points:** 4

**Notes:**  
- Should not allow overlapping blocks with existing appointments.
5️⃣ Update Profile
markdown
Copy code
**Title:**  
_As a doctor, I want to update my profile with specialization and contact info, so that patients have up-to-date information._

**Acceptance Criteria:**  
1. Editable fields for specialization, bio, and contact.  
2. System validates and saves changes.  
3. Updated info is visible to patients immediately.

**Priority:** High

**Story Points:** 3

**Notes:**  
- Consider image upload for profile picture.
6️⃣ View Patient Details
markdown
Copy code
**Title:**  
_As a doctor, I want to view patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**  
1. Doctor clicks on an appointment to see patient details.  
2. Details include name, age, medical history summary, and reason for visit.  
3. Info is read-only and securely displayed.

**Priority:** High

**Story Points:** 3

**Notes:**  
- Must comply with data privacy laws.

  
  
