// ✅ patientServices.js
// Location: app/src/main/resources/static/js/services/patientServices.js

import { API_BASE_URL } from "../config/config.js";

const PATIENT_API = API_BASE_URL + '/patient';

// ✅ Register a new patient
export async function patientSignup(data) {
  try {
    const response = await fetch(`${PATIENT_API}/signup`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });

    const result = await response.json();

    if (response.ok) {
      return {
        success: true,
        message: result.message || "Patient signed up successfully."
      };
    } else {
      return {
        success: false,
        message: result.message || "Failed to sign up patient."
      };
    }
  } catch (error) {
    console.error("Error during patient signup:", error);
    return {
      success: false,
      message: "Error during patient signup. Please try again."
    };
  }
}

// ✅ Log in a patient
export async function patientLogin(data) {
  try {
    const response = await fetch(`${PATIENT_API}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    return response; // caller handles status and token extraction
  } catch (error) {
    console.error("Error during patient login:", error);
    throw error; // let caller handle unexpected network errors
  }
}

// ✅ Get current patient data using token
export async function getPatientData(token) {
  try {
    const response = await fetch(`${PATIENT_API}?token=${token}`);
    if (response.ok) {
      const patient = await response.json();
      return patient;
    } else {
      console.error("Failed to fetch patient data:", response.status);
      return null;
    }
  } catch (error) {
    console.error("Error fetching patient data:", error);
    return null;
  }
}

// ✅ Get patient appointments (or doctor view of appointments)
export async function getPatientAppointments(id, token, user) {
  try {
    const response = await fetch(`${PATIENT_API}/appointments/${id}?token=${token}&user=${user}`);
    if (response.ok) {
      const appointments = await response.json();
      return appointments;
    } else {
      console.error("Failed to fetch appointments:", response.status);
      return null;
    }
  } catch (error) {
    console.error("Error fetching appointments:", error);
    return null;
  }
}

// ✅ Filter appointments by condition and name
export async function filterAppointments(condition, name, token) {
  try {
    const queryParams = new URLSearchParams();
    if (condition) queryParams.append("condition", condition);
    if (name) queryParams.append("name", name);

    const response = await fetch(`${PATIENT_API}/appointments/filter?${queryParams.toString()}&token=${token}`);

    if (response.ok) {
      const appointments = await response.json();
      return appointments;
    } else {
      console.error("Failed to filter appointments:", response.status);
      return [];
    }
  } catch (error) {
    console.error("Error filtering appointments:", error);
    alert("Error filtering appointments. Please try again.");
    return [];
  }
}
