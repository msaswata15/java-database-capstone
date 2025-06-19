package com.project.back_end.service;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repository.AppointmentRepository;
import com.project.back_end.repository.DoctorRepository;
import com.project.back_end.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * ✅ Book a new appointment.
     * @param appointment the appointment object to book
     * @return 1 if success, 0 if failed
     */
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ✅ Update an existing appointment.
     * @param appointment the appointment object with updated data
     * @return response entity with message
     */
    public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
        Map<String, String> response = new HashMap<>();
        Optional<Appointment> existing = appointmentRepository.findById(appointment.getId());
        if (existing.isPresent()) {
            appointmentRepository.save(appointment);
            response.put("message", "Appointment updated successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Appointment not found.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * ✅ Cancel an existing appointment.
     * @param id appointment ID to cancel
     * @param token JWT token for authentication
     * @return response entity with message
     */
    public ResponseEntity<Map<String, String>> cancelAppointment(long id, String token) {
        Map<String, String> response = new HashMap<>();
        Optional<Appointment> existing = appointmentRepository.findById(id);
        if (existing.isPresent()) {
            Appointment appointment = existing.get();
            // Validate if the token belongs to the patient who booked this appointment
            String patientEmail = tokenService.extractUsername(token);
            Optional<Patient> patientOpt = patientRepository.findByEmail(patientEmail);
            if (patientOpt.isPresent() && Objects.equals(patientOpt.get().getId(), appointment.getPatientId())) {
                appointmentRepository.delete(appointment);
                response.put("message", "Appointment cancelled successfully.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Unauthorized to cancel this appointment.");
                return ResponseEntity.status(403).body(response);
            }
        } else {
            response.put("message", "Appointment not found.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * ✅ Get appointments for a doctor on a specific date, optionally filtered by patient name.
     * @param pname patient name to filter by
     * @param date appointment date
     * @param token JWT token for doctor auth
     * @return map containing list of appointments
     */
    public Map<String, Object> getAppointment(String pname, LocalDate date, String token) {
        Map<String, Object> response = new HashMap<>();
        String doctorEmail = tokenService.extractUsername(token);
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(doctorEmail);

        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(LocalTime.MAX);
            List<Appointment> appointments;

            if (pname == null || pname.equalsIgnoreCase("null") || pname.isEmpty()) {
                appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                        doctor.getId(), start, end);
            } else {
                appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetweenAndPatientNameContainingIgnoreCase(
                        doctor.getId(), start, end, pname);
            }

            response.put("appointments", appointments);
            return response;
        } else {
            response.put("appointments", Collections.emptyList());
            return response;
        }
    }
}
