package com.project.back_end.controller;

import com.project.back_end.models.Prescription;
import com.project.back_end.service.PrescriptionService;
import com.project.back_end.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.path}prescription")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final ServiceClass service;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService, ServiceClass service) {
        this.prescriptionService = prescriptionService;
        this.service = service;
    }

    /**
     * ✅ 1. Save Prescription
     * POST /prescription/{token}
     */
    @PostMapping("/{token}")
    public ResponseEntity<?> savePrescription(
            @PathVariable String token,
            @RequestBody Prescription prescription) {

        if (!service.validateToken(token, "doctor")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        int status = prescriptionService.savePrescription(prescription);
        if (status == 1) {
            return ResponseEntity.ok(Map.of("message", "Prescription saved successfully"));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error saving prescription"));
        }
    }

    /**
     * ✅ 2. Get Prescription by Appointment ID
     * GET /prescription/{appointmentId}/{token}
     */
    @GetMapping("/{appointmentId}/{token}")
    public ResponseEntity<?> getPrescription(
            @PathVariable long appointmentId,
            @PathVariable String token) {

        if (!service.validateToken(token, "doctor")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        Prescription prescription = prescriptionService.getPrescription(appointmentId);
        if (prescription != null) {
            return ResponseEntity.ok(prescription);
        } else {
            return ResponseEntity.ok(Map.of("message", "No prescription found for this appointment"));
        }
    }

}
