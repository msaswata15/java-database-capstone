/**
 * header.js
 * Reusable dynamic header for all pages
 */

export function renderHeader() {
    const headerDiv = document.getElementById("header");
    if (!headerDiv) return;
  
    // ✅ 1. If homepage, clear local storage role/token
    if (window.location.pathname.endsWith("/")) {
      localStorage.removeItem("userRole");
      localStorage.removeItem("token");
    }
  
    // ✅ 2. Get role & token
    const role = localStorage.getItem("userRole");
    const token = localStorage.getItem("token");
  
    // ✅ 3. Check for invalid state → clear + redirect
    if ((role === "loggedPatient" || role === "admin" || role === "doctor") && !token) {
      localStorage.removeItem("userRole");
      alert("Session expired or invalid login. Please log in again.");
      window.location.href = "/";
      return;
    }
  
    // ✅ 4. Build role-based header
    let headerContent = `<nav class="header-nav">`;
  
    if (role === "admin") {
      headerContent += `
        <button id="addDocBtn" class="adminBtn">Add Doctor</button>
        <a href="#" id="logoutBtn">Logout</a>
      `;
    } else if (role === "doctor") {
      headerContent += `
        <a href="/doctor/doctorDashboard.html" id="doctorHome">Home</a>
        <a href="#" id="logoutBtn">Logout</a>
      `;
    } else if (role === "patient") {
      headerContent += `
        <a href="/login.html" id="loginBtn">Login</a>
        <a href="/signup.html" id="signupBtn">Sign Up</a>
      `;
    } else if (role === "loggedPatient") {
      headerContent += `
        <a href="/pages/patientDashboard.html" id="patientHome">Home</a>
        <a href="/pages/appointments.html" id="appointmentsBtn">Appointments</a>
        <a href="#" id="logoutPatientBtn">Logout</a>
      `;
    }
  
    headerContent += `</nav>`;
  
    // ✅ 5. Inject header HTML
    headerDiv.innerHTML = headerContent;
  
    // ✅ 6. Attach event listeners
    attachHeaderButtonListeners();
  }
  
  /**
   * Attach event listeners for dynamic buttons
   */
  function attachHeaderButtonListeners() {
    // Add Doctor button for admin
    const addDocBtn = document.getElementById("addDocBtn");
    if (addDocBtn) {
      addDocBtn.addEventListener("click", () => openModal("addDoctor"));
    }
  
    // Logout for admin & doctor
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
      logoutBtn.addEventListener("click", logout);
    }
  
    // Logout for loggedPatient
    const logoutPatientBtn = document.getElementById("logoutPatientBtn");
    if (logoutPatientBtn) {
      logoutPatientBtn.addEventListener("click", logoutPatient);
    }
  
    // (Optional) login/signup can have more handlers if needed
  }
  
  /**
   * Logout: removes token + role → redirect to homepage
   */
  function logout() {
    localStorage.removeItem("userRole");
    localStorage.removeItem("token");
    window.location.href = "/";
  }
  
  /**
   * Logout for patient: keep role as "patient"
   */
  function logoutPatient() {
    localStorage.removeItem("token");
    localStorage.setItem("userRole", "patient");
    window.location.href = "/pages/patientDashboard.html";
  }
  