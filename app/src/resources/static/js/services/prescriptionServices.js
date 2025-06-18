// prescriptionServices.js
import { API_BASE_URL } from '../config/config.js'

const PRESCRITION_API = API_BASE_URL + "/prescription"
export async function savePrescription(prescription, token) {
  try {
    const response = await fetch(`${PRESCRITION_API}/${token}`, {
      method: "POST",
      headers: {
        "Content-type": "application/json"
      },
      body: JSON.stringify(prescription)
    });
    const result = await response.json();
    return { success: response.ok, message: result.message }
  }
  catch (error) {
    console.error("Error :: savePrescription :: ", error)
    return { success: false, message: result.message }
  }
}

export async function getPrescription(appointmentId, token) {
  try {
    const response = await fetch(`${PRESCRITION_API}/${appointmentId}/${token}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) {
      const errorData = await response.json();
      console.error("Failed to fetch prescription:", errorData);
      throw new Error(errorData.message || "Unable to fetch prescription");
    }

    const result = await response.json();
    console.log(result)
    console.log(result)
    return result; // This should be your prescription object
  } catch (error) {
    console.error("Error :: getPrescription ::", error);
    throw error;
  }
}
