package com.project.back_end.service;

import com.project.back_end.dto.Login;
import com.project.back_end.models.Admin;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repository.AdminRepository;
import com.project.back_end.repository.DoctorRepository;
import com.project.back_end.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ServiceClass {

    private final TokenService tokenService;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public ServiceClass(TokenService tokenService,
                        AdminRepository adminRepository,
                        DoctorRepository doctorRepository,
                        PatientRepository patientRepository,
                        DoctorService doctorService,
                        PatientService patientService) {
        this.tokenService = tokenService;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    /**
     * ✅ Validate JWT token for given user type
     */
    public ResponseEntity<Map<String, String>> validateToken(String token, String user) {
        Map<String, String> response = new HashMap<>();
        if (!tokenService.validateToken(token, user)) {
            response.put("message", "Invalid or expired token");
            return ResponseEntity.status(401).body(response);
        }
        response.put("message", "Token is valid");
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ Validate Admin credentials and return token if valid
     */
    public ResponseEntity<Map<String, String>> validateAdmin(Admin receivedAdmin) {
        Map<String, String> response = new HashMap<>();
        Optional<Admin> adminOpt = adminRepository.findByUsername(receivedAdmin.getUsername());

        if (adminOpt.isPresent() && adminOpt.get().getPassword().equals(receivedAdmin.getPassword())) {
            String token = tokenService.generateToken(receivedAdmin.getUsername(), "admin");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * ✅ Filter doctors by name, specialty, and time
     */
    public Map<String, Object> filterDoctor(String name, String specialty, String time) {
        List<Doctor> doctors = doctorService.filterDoctorsByNameSpecialityAndTime(name, specialty, time);
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", doctors);
        return response;
    }

    /**
     * ✅ Validate if an appointment time is available for given doctor
     */
    public int validateAppointment(Appointment appointment) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(appointment.getDoctorId());
        if (doctorOpt.isEmpty()) {
            return -1; // Doctor not found
        }
        Doctor doctor = doctorOpt.get();
        List<String> availableTimes = doctorService.getDoctorAvailability(doctor);

        String appointmentTime = appointment.getAppointmentTime().toLocalTime().toString();
        if (availableTimes.contains(appointmentTime)) {
            return 1; // Valid
        } else {
            return 0; // Time unavailable
        }
    }

    /**
     * ✅ Check if patient with same email or phone already exists
     */
    public boolean validatePatient(Patient patient) {
        Patient found = patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone());
        return found == null;
    }

    /**
     * ✅ Validate patient login and return token if valid
     */
    public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {
        Map<String, String> response = new HashMap<>();
        Patient patient = patientRepository.findByEmail(login.getEmail());

        if (patient != null && patient.getPassword().equals(login.getPassword())) {
            String token = tokenService.generateToken(patient.getEmail(), "patient");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(401).body(response);
        }
    }

    /**
     * ✅ Filter patient appointments by condition and/or doctor name
     */
    public ResponseEntity<Map<String, Object>> filterPatient(String condition, String name, String token) {
        String email = tokenService.extractUsername(token);
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);

        if (patientOpt.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Unauthorized");
            return ResponseEntity.status(403).body(response);
        }

        long patientId = patientOpt.get().getId();

        if (condition != null && !condition.isEmpty() && name != null && !name.isEmpty()) {
            return patientService.filterByDoctorAndCondition(condition, name, patientId);
        } else if (condition != null && !condition.isEmpty()) {
            return patientService.filterByCondition(condition, patientId);
        } else if (name != null && !name.isEmpty()) {
            return patientService.filterByDoctor(name, patientId);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Please provide at least one filter");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
