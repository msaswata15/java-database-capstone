package com.project.back_end.services;

import com.project.back_end.models.Prescription;
import com.project.back_end.repo.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int savePrescription(Prescription prescription) {
        try {
            prescriptionRepository.save(prescription);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ✅ Get a prescription by appointment ID.
     *
     * @param appointmentId The appointment ID to look up
     * @return ResponseEntity with prescription or error message
     */
    public Prescription getPrescription(Long appointmentId) {
        try {
            java.util.List<Prescription> list = prescriptionRepository.findByAppointmentId(appointmentId);
            return (list != null && !list.isEmpty()) ? list.get(0) : null;
        } catch (Exception e) {
            return null;
        }
    }

}
