// ✅ patientDashboard.js
// Location: app/src/main/resources/static/js/patientDashboard.js

import { createDoctorCard } from "./components/doctorCard.js";
import { openModal } from "./components/modals.js";
import { getDoctors, filterDoctors } from "./services/doctorServices.js";
import { patientLogin, patientSignup } from "./services/patientServices.js";

// === Load Doctor Cards ===
async function loadDoctorCards() {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  try {
    const doctors = await getDoctors();

    if (!doctors || doctors.length === 0) {
      contentDiv.innerHTML = "<p>No doctors available at the moment.</p>";
      return;
    }

    doctors.forEach(doc => {
      const card = createDoctorCard(doc);
      contentDiv.appendChild(card);
    });
  } catch (error) {
    console.error("Error loading doctors:", error);
    contentDiv.innerHTML = "<p>Error loading doctors. Please try again later.</p>";
  }
}

// === Filter Doctors On Change ===
async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar").value.trim();
  const time = document.getElementById("filterTime").value;
  const specialty = document.getElementById("filterSpecialty").value;

  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  try {
    const doctors = await filterDoctors(name, time, specialty);

    if (!doctors || doctors.length === 0) {
      contentDiv.innerHTML = "<p>No doctors found with the given filters.</p>";
      return;
    }

    doctors.forEach(doc => {
      const card = createDoctorCard(doc);
      contentDiv.appendChild(card);
    });
  } catch (error) {
    console.error("Error filtering doctors:", error);
    contentDiv.innerHTML = "<p>Error fetching filtered doctors.</p>";
  }
}

// === Handle Patient Signup ===
window.signupPatient = async function () {
  const name = document.getElementById("signupName").value.trim();
  const email = document.getElementById("signupEmail").value.trim();
  const password = document.getElementById("signupPassword").value.trim();
  const phone = document.getElementById("signupPhone").value.trim();
  const address = document.getElementById("signupAddress").value.trim();

  const data = { name, email, password, phone, address };

  try {
    const response = await patientSignup(data);
    if (response.success) {
      alert(response.message);
      // Assuming closeModal exists — or use your modal logic:
      openModal("close");
      window.location.reload();
    } else {
      alert(response.message || "Signup failed.");
    }
  } catch (error) {
    console.error("Signup error:", error);
    alert("An unexpected error occurred during signup.");
  }
};

// === Handle Patient Login ===
window.loginPatient = async function () {
  const email = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPassword").value.trim();

  const data = { email, password };

  try {
    const res = await patientLogin(data);
    if (res.ok) {
      const json = await res.json();
      localStorage.setItem("token", json.token);
      localStorage.setItem("userRole", "loggedPatient");
      window.location.href = "loggedPatientDashboard.html";
    } else {
      alert("Invalid credentials. Please try again.");
    }
  } catch (error) {
    console.error("Login error:", error);
    alert("An unexpected error occurred during login.");
  }
};

// === Event Listeners ===
document.addEventListener("DOMContentLoaded", () => {
  // Load all doctors initially
  loadDoctorCards();

  // Signup modal trigger
  const signupBtn = document.getElementById("patientSignup");
  if (signupBtn) signupBtn.addEventListener("click", () => openModal("patientSignup"));

  // Login modal trigger
  const loginBtn = document.getElementById("patientLogin");
  if (loginBtn) loginBtn.addEventListener("click", () => openModal("patientLogin"));

  // Filters and search bar
  document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
  document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
  document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);
});
