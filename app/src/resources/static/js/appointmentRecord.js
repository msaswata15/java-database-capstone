// appointmentRecord.js
import { getAppointments } from "./components/appointmentRow.js";
import { getAppointmentRecord } from "./services/appointmentRecordService.js";

const tableBody = document.getElementById("patientTableBody");
const filterSelect = document.getElementById("appointmentFilter");

async function loadAppointments(filter = "upcoming") {
  const appointments = await getAppointmentRecord();

  if (!appointments || appointments.length === 0) {
    tableBody.innerHTML = `<tr><td class="noPatientRecord" colspan='5'>No appointments found.</td></tr>`;
    return;
  }

  const today = new Date().setHours(0, 0, 0, 0);
  let filteredAppointments = appointments;

  if (filter === "upcoming") {
    filteredAppointments = appointments.filter(app => new Date(app.date) >= today);
  } else if (filter === "past") {
    filteredAppointments = appointments.filter(app => new Date(app.date) < today);
  }

  if (filteredAppointments.length === 0) {
    tableBody.innerHTML = `<tr><td class="noPatientRecord" colspan='5'>No ${filter} appointments found.</td></tr>`;
    return;
  }

  tableBody.innerHTML = "";
  filteredAppointments.forEach(appointment => {
    const row = getAppointments(appointment);
    tableBody.appendChild(row);
  });
}

// Handle filter change
filterSelect.addEventListener("change", (e) => {
  const selectedFilter = e.target.value;
  loadAppointments(selectedFilter);
});

// Load upcoming appointments by default
loadAppointments("upcoming");
