package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.DTO.Login;
import com.project.back_end.models.Appointment;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * ✅ Get available slots for a doctor on a given date.
     */
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {
        List<String> allSlots = Arrays.asList(
                "09:00 AM", "10:00 AM", "11:00 AM",
                "12:00 PM", "01:00 PM", "02:00 PM",
                "03:00 PM", "04:00 PM", "05:00 PM");

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                doctorId, start, end);

        // Convert booked slots to readable format
        List<String> bookedReadable = appointments.stream()
                .map(a -> a.getAppointmentTime().toLocalTime().format(
                    java.time.format.DateTimeFormatter.ofPattern("hh:mm a")))
                .collect(Collectors.toList());

        // Return slots not in booked slots
        return allSlots.stream()
                .filter(slot -> !bookedReadable.contains(slot))
                .collect(Collectors.toList());
    }

    /**
     * ✅ Save a new doctor.
     */
    public int saveDoctor(Doctor doctor) {
        Optional<Doctor> existing = doctorRepository.findByEmail(doctor.getEmail());
        if (existing.isPresent()) {
            return -1; // Doctor already exists
        }
        try {
            doctorRepository.save(doctor);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * ✅ Update an existing doctor.
     */
    public int updateDoctor(Doctor doctor) {
        Optional<Doctor> existing = doctorRepository.findById(doctor.getId());
        if (existing.isPresent()) {
            doctorRepository.save(doctor);
            return 1;
        } else {
            return -1; // Not found
        }
    }

    /**
     * ✅ Get all doctors.
     */
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * ✅ Delete doctor by ID.
     */
    public int deleteDoctor(long id) {
        Optional<Doctor> existing = doctorRepository.findById(id);
        if (existing.isPresent()) {
            appointmentRepository.deleteAllByDoctorId(id);
            doctorRepository.deleteById(id);
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * ✅ Validate doctor login credentials.
     */
    public ResponseEntity<Map<String, String>> validateDoctor(Login login) {
        Map<String, String> response = new HashMap<>();
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(login.getEmail());
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            if (doctor.getPassword().equals(login.getPassword())) {
                String token = tokenService.generateToken(doctor.getEmail(), "DOCTOR");
                response.put("token", token);
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid password");
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            response.put("message", "Doctor not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * ✅ Find doctors by partial name.
     */
    public Map<String, Object> findDoctorByName(String name) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findByNameLike("%" + name + "%");
        response.put("doctors", doctors);
        return response;
    }

    /**
     * ✅ Filter by name, specialty and time.
     */
    public Map<String, Object> filterDoctorsByNameSpecialtyAndTime(String name, String specialty, String amOrPm) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        List<Doctor> filtered = filterDoctorByTime(doctors, amOrPm);
        response.put("doctors", filtered);
        return response;
    }

    /**
     * ✅ Filter by name and time.
     */
    public Map<String, Object> filterDoctorByNameAndTime(String name, String amOrPm) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findByNameLike("%" + name + "%");
        List<Doctor> filtered = filterDoctorByTime(doctors, amOrPm);
        response.put("doctors", filtered);
        return response;
    }

    /**
     * ✅ Filter by name and specialty.
     */
    public Map<String, Object> filterDoctorByNameAndSpecialty(String name, String specialty) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);
        response.put("doctors", doctors);
        return response;
    }

    /**
     * ✅ Filter by specialty and time.
     */
    public Map<String, Object> filterDoctorBySpecialtyAndTime(String specialty, String amOrPm) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        List<Doctor> filtered = filterDoctorByTime(doctors, amOrPm);
        response.put("doctors", filtered);
        return response;
    }

    /**
     * ✅ Filter by specialty only.
     */
    public Map<String, Object> filterDoctorBySpecialty(String specialty) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
        response.put("doctors", doctors);
        return response;
    }

    /**
     * ✅ Filter all doctors by time.
     */
    public Map<String, Object> filterDoctorsByTime(String amOrPm) {
        Map<String, Object> response = new HashMap<>();
        List<Doctor> doctors = doctorRepository.findAll();
        List<Doctor> filtered = filterDoctorByTime(doctors, amOrPm);
        response.put("doctors", filtered);
        return response;
    }

    /**
     * ✅ Private helper to filter doctors by AM/PM.
     */
    private List<Doctor> filterDoctorByTime(List<Doctor> doctors, String amOrPm) {
        return doctors.stream().filter(doctor -> {
            List<String> slots = doctor.getAvailableTimes();
            return slots.stream().anyMatch(slot -> slot.endsWith(amOrPm.toUpperCase()));
        }).collect(Collectors.toList());
    }
}
