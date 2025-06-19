// ✅ doctorServices.js
// Location: app/src/main/resources/static/js/services/doctorServices.js

import { API_BASE_URL } from "../config/config.js";

const DOCTOR_API = API_BASE_URL + '/doctor';

// ✅ Get all doctors
export async function getDoctors() {
  try {
    const response = await fetch(DOCTOR_API);
    if (response.ok) {
      const doctors = await response.json();
      return doctors;
    } else {
      console.error("Failed to fetch doctors:", response.status);
      return [];
    }
  } catch (error) {
    console.error("Error fetching doctors:", error);
    return [];
  }
}

// ✅ Delete a doctor by ID
export async function deleteDoctor(id, token) {
  try {
    const response = await fetch(`${DOCTOR_API}/${id}?token=${token}`, {
      method: 'DELETE'
    });
    if (response.ok) {
      const result = await response.json();
      return {
        success: true,
        message: result.message || "Doctor deleted successfully"
      };
    } else {
      return {
        success: false,
        message: `Failed to delete doctor. Status: ${response.status}`
      };
    }
  } catch (error) {
    console.error("Error deleting doctor:", error);
    return {
      success: false,
      message: "Error deleting doctor"
    };
  }
}

// ✅ Save (Add) a new doctor
export async function saveDoctor(doctor, token) {
  try {
    const response = await fetch(`${DOCTOR_API}?token=${token}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(doctor)
    });
    if (response.ok) {
      const result = await response.json();
      return {
        success: true,
        message: result.message || "Doctor saved successfully"
      };
    } else {
      return {
        success: false,
        message: `Failed to save doctor. Status: ${response.status}`
      };
    }
  } catch (error) {
    console.error("Error saving doctor:", error);
    return {
      success: false,
      message: "Error saving doctor"
    };
  }
}

// ✅ Filter doctors by name, time, specialty
export async function filterDoctors(name, time, specialty) {
  try {
    // Use query params for flexibility
    const queryParams = new URLSearchParams();
    if (name) queryParams.append("name", name);
    if (time) queryParams.append("time", time);
    if (specialty) queryParams.append("specialty", specialty);

    const response = await fetch(`${DOCTOR_API}/filter?${queryParams.toString()}`);
    if (response.ok) {
      const doctors = await response.json();
      return doctors;
    } else {
      console.error("Failed to filter doctors:", response.status);
      return [];
    }
  } catch (error) {
    console.error("Error filtering doctors:", error);
    return [];
  }
}
