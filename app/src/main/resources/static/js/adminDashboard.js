// ✅ adminDashboard.js
// Location: app/src/main/resources/static/js/adminDashboard.js

import { openModal } from "../components/modals.js";
import { getDoctors, filterDoctors, saveDoctor } from "./services/doctorServices.js";
import { createDoctorCard } from "../components/doctorCard.js";

// === Bind Add Doctor Button ===
document.getElementById('addDocBtn').addEventListener('click', () => {
  openModal('addDoctor');
});

// === Load all doctors on page load ===
window.onload = () => {
  loadDoctorCards();
};

// === Fetch and display all doctors ===
async function loadDoctorCards() {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "<p>Loading doctors...</p>";

  try {
    const doctors = await getDoctors();
    renderDoctorCards(doctors);
  } catch (error) {
    console.error("Error loading doctors:", error);
    contentDiv.innerHTML = "<p>Error loading doctors. Please try again.</p>";
  }
}

// === Render helper: show doctors list ===
function renderDoctorCards(doctors) {
  const contentDiv = document.getElementById("content");
  contentDiv.innerHTML = "";

  if (doctors && doctors.length > 0) {
    doctors.forEach(doc => {
      const card = createDoctorCard(doc);
      contentDiv.appendChild(card);
    });
  } else {
    contentDiv.innerHTML = "<p>No doctors found.</p>";
  }
}

// === Filter and search listeners ===
document.getElementById("searchBar").addEventListener("input", filterDoctorsOnChange);
document.getElementById("filterTime").addEventListener("change", filterDoctorsOnChange);
document.getElementById("filterSpecialty").addEventListener("change", filterDoctorsOnChange);

// === Filter handler ===
async function filterDoctorsOnChange() {
  const name = document.getElementById("searchBar").value.trim();
  const time = document.getElementById("filterTime").value;
  const specialty = document.getElementById("filterSpecialty").value;

  try {
    const filtered = await filterDoctors(name, time, specialty);
    renderDoctorCards(filtered);
  } catch (error) {
    console.error("Error filtering doctors:", error);
    alert("Failed to filter doctors. Please try again.");
  }
}

// === Add Doctor Form Handler ===
window.adminAddDoctor = async function () {
  const name = document.getElementById("doctorName").value.trim();
  const email = document.getElementById("doctorEmail").value.trim();
  const password = document.getElementById("doctorPassword").value.trim();
  const mobileNo = document.getElementById("doctorMobileNo").value.trim();
  const specialty = document.getElementById("doctorSpecialty").value.trim();

  // Collect availability checkboxes
  const checkboxes = document.querySelectorAll("input[name='availability']:checked");
  const availability = Array.from(checkboxes).map(cb => cb.value);

  const token = localStorage.getItem("token");

  if (!token) {
    alert("Session expired. Please log in again.");
    return;
  }

  const doctor = {
    name,
    email,
    password,
    mobileNo,
    specialty,
    availability
  };

  try {
    const result = await saveDoctor(doctor, token);
    if (result.success) {
      alert("Doctor added successfully!");
      // Optionally close modal if you have a closeModal function
      window.location.reload(); // Or reload doctor list: loadDoctorCards();
    } else {
      alert("Failed to add doctor: " + result.message);
    }
  } catch (error) {
    console.error("Error adding doctor:", error);
    alert("Error adding doctor. Please try again.");
  }
};
