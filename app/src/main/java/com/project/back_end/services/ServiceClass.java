package com.project.back_end.services;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
     */    public boolean validateToken(String token, String user) {
        return tokenService.validateToken(token, user);
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
    public Map<String, Object> filterDoctor(String name, String specialty, String time) {        return doctorService.filterDoctorsByNameSpecialtyAndTime(name, specialty, time);
    }

    /**
     * ✅ Validate if an appointment time is available for given doctor
     */
    public int validateAppointment(Appointment appointment) {        if (!doctorRepository.findById(appointment.getDoctorId()).isPresent()) {
            return -1; // Doctor not found
        }List<String> availableTimes = doctorService.getDoctorAvailability(
            appointment.getDoctorId(), 
            appointment.getAppointmentTime().toLocalDate()
        );

        String appointmentTime = appointment.getAppointmentTime().toLocalTime().format(
            java.time.format.DateTimeFormatter.ofPattern("hh:mm a")
        );
        
        if (availableTimes.contains(appointmentTime)) {
            return 1; // Valid
        } else {
            return 0; // Time unavailable
        }
    }

    /**
     * ✅ Check if patient with same email or phone already exists
     */    public boolean validatePatient(Patient patient) {
        return patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone()).isEmpty();
    }

    /**
     * ✅ Validate patient login and return token if valid
     */
    public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {
        Map<String, String> response = new HashMap<>();
        Optional<Patient> patientOpt = patientRepository.findByEmail(login.getEmail());

        if (patientOpt.isPresent() && patientOpt.get().getPassword().equals(login.getPassword())) {
            String token = tokenService.generateToken(patientOpt.get().getEmail(), "patient");
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
