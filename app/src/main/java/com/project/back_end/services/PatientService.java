package com.project.back_end.services;

import com.project.back_end.DTO.AppointmentDTO;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * ✅ Create a new patient.
     * 
     * @param patient Patient object to save
     * @return 1 on success, 0 on failure
     */
    public int createPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ✅ Get appointments for a patient, validated by token.
     */
    public ResponseEntity<Map<String, Object>> getPatientAppointment(Long id, String token) {
        Map<String, Object> response = new HashMap<>();
        String email = tokenService.extractUsername(token);
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);

        if (patientOpt.isPresent() && Objects.equals(patientOpt.get().getId(), id)) {
            List<Appointment> appointments = appointmentRepository.findByPatientId(id);
            List<AppointmentDTO> dtos = appointments.stream()
                    .map(app -> new AppointmentDTO(
                        app.getId(),
                        app.getDoctor() != null ? app.getDoctor().getId() : null,
                        app.getDoctorName(),
                        app.getPatient() != null ? app.getPatient().getId() : null,
                        app.getPatient() != null ? app.getPatient().getName() : null,
                        app.getPatient() != null ? app.getPatient().getEmail() : null,
                        app.getPatient() != null ? app.getPatient().getPhone() : null,
                        app.getPatient() != null ? app.getPatient().getAddress() : null,
                        app.getAppointmentTime(),
                        app.getStatus()
                    ))
                    .collect(Collectors.toList());
            response.put("appointments", dtos);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Unauthorized or patient not found.");
            return ResponseEntity.status(403).body(response);
        }
    }

    /**
     * ✅ Filter appointments by condition (past or future) for a patient.
     */
    public ResponseEntity<Map<String, Object>> filterByCondition(String condition, Long id) {
        Map<String, Object> response = new HashMap<>();
        List<Appointment> appointments = appointmentRepository.findByPatientId(id);

        LocalDateTime now = LocalDateTime.now();
        List<Appointment> filtered;

        if (condition.equalsIgnoreCase("past")) {
            filtered = appointments.stream()
                    .filter(app -> app.getAppointmentTime().isBefore(now))
                    .collect(Collectors.toList());
        } else {
            filtered = appointments.stream()
                    .filter(app -> app.getAppointmentTime().isAfter(now))
                    .collect(Collectors.toList());
        }

        List<AppointmentDTO> dtos = filtered.stream()
                .map(app -> new AppointmentDTO(
                    app.getId(),
                    app.getDoctor() != null ? app.getDoctor().getId() : null,
                    app.getDoctorName(),
                    app.getPatient() != null ? app.getPatient().getId() : null,
                    app.getPatient() != null ? app.getPatient().getName() : null,
                    app.getPatient() != null ? app.getPatient().getEmail() : null,
                    app.getPatient() != null ? app.getPatient().getPhone() : null,
                    app.getPatient() != null ? app.getPatient().getAddress() : null,
                    app.getAppointmentTime(),
                    app.getStatus()
                ))
                .collect(Collectors.toList());

        response.put("appointments", dtos);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Filter appointments by doctor's name for a patient.
     */
    public ResponseEntity<Map<String, Object>> filterByDoctor(String name, Long patientId) {
        Map<String, Object> response = new HashMap<>();
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        List<Appointment> filtered = appointments.stream()
                .filter(app -> app.getDoctorName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        List<AppointmentDTO> dtos = filtered.stream()
                .map(app -> new AppointmentDTO(
                    app.getId(),
                    app.getDoctor() != null ? app.getDoctor().getId() : null,
                    app.getDoctorName(),
                    app.getPatient() != null ? app.getPatient().getId() : null,
                    app.getPatient() != null ? app.getPatient().getName() : null,
                    app.getPatient() != null ? app.getPatient().getEmail() : null,
                    app.getPatient() != null ? app.getPatient().getPhone() : null,
                    app.getPatient() != null ? app.getPatient().getAddress() : null,
                    app.getAppointmentTime(),
                    app.getStatus()
                ))
                .collect(Collectors.toList());

        response.put("appointments", dtos);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Filter appointments by condition and doctor's name.
     */
    public ResponseEntity<Map<String, Object>> filterByDoctorAndCondition(String condition, String name,
            long patientId) {
        Map<String, Object> response = new HashMap<>();
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        LocalDateTime now = LocalDateTime.now();
        List<Appointment> filtered = appointments.stream()
                .filter(app -> app.getDoctorName().toLowerCase().contains(name.toLowerCase()))
                .filter(app -> {
                    if (condition.equalsIgnoreCase("past")) {
                        return app.getAppointmentTime().isBefore(now);
                    } else {
                        return app.getAppointmentTime().isAfter(now);
                    }
                })
                .collect(Collectors.toList());

        List<AppointmentDTO> dtos = filtered.stream()
                .map(app -> new AppointmentDTO(
                    app.getId(),
                    app.getDoctor() != null ? app.getDoctor().getId() : null,
                    app.getDoctorName(),
                    app.getPatient() != null ? app.getPatient().getId() : null,
                    app.getPatient() != null ? app.getPatient().getName() : null,
                    app.getPatient() != null ? app.getPatient().getEmail() : null,
                    app.getPatient() != null ? app.getPatient().getPhone() : null,
                    app.getPatient() != null ? app.getPatient().getAddress() : null,
                    app.getAppointmentTime(),
                    app.getStatus()
                ))
                .collect(Collectors.toList());

        response.put("appointments", dtos);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Get patient details by token.
     */
    public ResponseEntity<Map<String, Object>> getPatientDetails(String token) {
        Map<String, Object> response = new HashMap<>();
        String email = tokenService.extractUsername(token);
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);

        if (patientOpt.isPresent()) {
            response.put("patient", patientOpt.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Patient not found.");
            return ResponseEntity.badRequest().body(response);
        }
    }

    public boolean checkPatient(Patient patient) {
        // Returns true if patient exists (for controller logic)
        return patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone()).isPresent();
    }

}
