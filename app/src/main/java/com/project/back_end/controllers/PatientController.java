package com.project.back_end.controllers;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Patient;
import com.project.back_end.services.PatientService;
import com.project.back_end.services.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final ServiceClass service;

    @Autowired
    public PatientController(PatientService patientService, ServiceClass service) {
        this.patientService = patientService;
        this.service = service;
    }

    /**
     * ✅ 1. Get Patient Details
     */
    @GetMapping("/{token}")
    public ResponseEntity<?> getPatientDetails(@PathVariable String token) {
        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }
        return ResponseEntity.ok(patientService.getPatientDetails(token));
    }

    /**
     * ✅ 2. Register a New Patient
     */
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody Patient patient) {
        if (patientService.checkPatient(patient)) {
            return ResponseEntity.status(409).body(Map.of("error", "Patient with email or phone already exists"));
        }

        int status = patientService.createPatient(patient);
        if (status == 1) {
            return ResponseEntity.ok(Map.of("message", "Signup successful"));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }

    /**
     * ✅ 3. Patient Login
     */
    @PostMapping("/login")
    public ResponseEntity<?> patientLogin(@RequestBody Login login) {
        return service.validatePatientLogin(login);
    }

    /**
     * ✅ 4. Get Patient Appointments
     */
    @GetMapping("/{id}/{token}")
    public ResponseEntity<?> getPatientAppointments(
            @PathVariable long id,
            @PathVariable String token) {

        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        return ResponseEntity.ok(patientService.getPatientAppointment(id, token));
    }

    /**
     * ✅ 5. Filter Patient Appointments
     */
    @GetMapping("/filter/{condition}/{name}/{token}")
    public ResponseEntity<?> filterPatientAppointments(
            @PathVariable String condition,
            @PathVariable String name,
            @PathVariable String token) {

        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        return ResponseEntity.ok(service.filterPatient(condition, name, token));
    }

}
