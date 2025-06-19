package com.project.back_end.service;

import com.project.back_end.models.Prescription;
import com.project.back_end.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    /**
     * ✅ Save a new prescription.
     *
     * @param prescription The prescription object to save
     * @return ResponseEntity with result message
     */
    public ResponseEntity<Map<String, String>> savePrescription(Prescription prescription) {
        Map<String, String> response = new HashMap<>();
        try {
            prescriptionRepository.save(prescription);
            response.put("message", "Prescription saved");
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("message", "Failed to save prescription");
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * ✅ Get a prescription by appointment ID.
     *
     * @param appointmentId The appointment ID to look up
     * @return ResponseEntity with prescription or error message
     */
    public ResponseEntity<Map<String, Object>> getPrescription(Long appointmentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Prescription prescription = prescriptionRepository.findByAppointmentId(appointmentId);
            if (prescription != null) {
                response.put("prescription", prescription);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Prescription not found");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Failed to retrieve prescription");
            return ResponseEntity.status(500).body(response);
        }
    }

}
