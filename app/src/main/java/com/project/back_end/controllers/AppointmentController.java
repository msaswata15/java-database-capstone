package com.project.back_end.controller;

import com.project.back_end.models.Appointment;
import com.project.back_end.service.AppointmentService;
import com.project.back_end.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final ServiceClass service;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, ServiceClass service) {
        this.appointmentService = appointmentService;
        this.service = service;
    }

    /**
     * ✅ Get appointments for a doctor by date and patient name
     */
    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<?> getAppointments(
            @PathVariable String date,
            @PathVariable String patientName,
            @PathVariable String token) {

        if (!service.validateToken(token, "doctor")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        LocalDate localDate = LocalDate.parse(date);
        Map<String, Object> appointments = appointmentService.getAppointment(patientName, localDate, token);
        return ResponseEntity.ok(appointments);
    }

    /**
     * ✅ Book a new appointment
     */
    @PostMapping("/{token}")
    public ResponseEntity<?> bookAppointment(
            @RequestBody Appointment appointment,
            @PathVariable String token) {

        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        if (!service.validateAppointment(appointment)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid appointment details!"));
        }

        int status = appointmentService.bookAppointment(appointment);
        if (status == 1) {
            return ResponseEntity.status(201).body(Map.of("message", "Appointment booked successfully!"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to book appointment!"));
        }
    }

    /**
     * ✅ Update an appointment
     */
    @PutMapping("/{token}")
    public ResponseEntity<?> updateAppointment(
            @RequestBody Appointment appointment,
            @PathVariable String token) {

        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        return appointmentService.updateAppointment(appointment);
    }

    /**
     * ✅ Cancel an appointment
     */
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable long id,
            @PathVariable String token) {

        if (!service.validateToken(token, "patient")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        return appointmentService.cancelAppointment(id, token);
    }

}
