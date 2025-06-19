// ✅ doctorDashboard.js
// Location: app/src/main/resources/static/js/doctorDashboard.js

import { getAllAppointments } from "./services/appointmentRecordService.js";
import { createPatientRow } from "./components/patientRows.js";

// === Global Variables ===
const patientTableBody = document.getElementById("patientTableBody");
let selectedDate = new Date().toISOString().split('T')[0]; // format: YYYY-MM-DD
let token = localStorage.getItem("token");
let patientName = null;

// === Search Bar ===
document.getElementById("searchBar").addEventListener("input", () => {
  const searchValue = document.getElementById("searchBar").value.trim();
  patientName = searchValue === "" ? "null" : searchValue;
  loadAppointments();
});

// === "Today’s Appointments" Button ===
document.getElementById("todayButton").addEventListener("click", () => {
  selectedDate = new Date().toISOString().split('T')[0];
  document.getElementById("datePicker").value = selectedDate;
  loadAppointments();
});

// === Date Picker ===
document.getElementById("datePicker").addEventListener("change", (e) => {
  selectedDate = e.target.value;
  loadAppointments();
});

// === Load Appointments ===
async function loadAppointments() {
  // Clear previous table rows
  patientTableBody.innerHTML = "";

  try {
    const appointments = await getAllAppointments(selectedDate, patientName, token);

    if (!appointments || appointments.length === 0) {
      const row = document.createElement("tr");
      const cell = document.createElement("td");
      cell.colSpan = 5;
      cell.textContent = "No Appointments found for selected date.";
      row.appendChild(cell);
      patientTableBody.appendChild(row);
      return;
    }

    appointments.forEach(app => {
      const row = createPatientRow(app);
      patientTableBody.appendChild(row);
    });

  } catch (error) {
    console.error("Error loading appointments:", error);
    const row = document.createElement("tr");
    const cell = document.createElement("td");
    cell.colSpan = 5;
    cell.textContent = "Error loading appointments. Please try again.";
    row.appendChild(cell);
    patientTableBody.appendChild(row);
  }
}

// === Initial Load on Page Ready ===
document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("datePicker").value = selectedDate;
  loadAppointments();
});
