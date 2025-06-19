package com.project.back_end.controller;

import com.project.back_end.dto.Login;
import com.project.back_end.models.Doctor;
import com.project.back_end.service.DoctorService;
import com.project.back_end.service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final ServiceClass service;

    @Autowired
    public DoctorController(DoctorService doctorService, ServiceClass service) {
        this.doctorService = doctorService;
        this.service = service;
    }

    /**
     * ✅ 1. Get Doctor Availability
     */
    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable long doctorId,
            @PathVariable String date,
            @PathVariable String token) {

        if (!service.validateToken(token, user)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(doctorService.getDoctorAvailability(user, doctorId, localDate));
    }

    /**
     * ✅ 2. Get List of Doctors
     */
    @GetMapping
    public ResponseEntity<?> getDoctors() {
        return ResponseEntity.ok(Map.of("doctors", doctorService.getDoctors()));
    }

    /**
     * ✅ 3. Add New Doctor
     */
    @PostMapping("/{token}")
    public ResponseEntity<?> addDoctor(
            @RequestBody Doctor doctor,
            @PathVariable String token) {

        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        int status = doctorService.saveDoctor(doctor);
        if (status == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor added to db"));
        } else if (status == -1) {
            return ResponseEntity.status(409).body(Map.of("error", "Doctor already exists"));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "Some internal error occurred"));
        }
    }

    /**
     * ✅ 4. Doctor Login
     */
    @PostMapping("/login")
    public ResponseEntity<?> doctorLogin(@RequestBody Login login) {
        return doctorService.validateDoctor(login);
    }

    /**
     * ✅ 5. Update Doctor Details
     */
    @PutMapping("/{token}")
    public ResponseEntity<?> updateDoctor(
            @RequestBody Doctor doctor,
            @PathVariable String token) {

        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        int status = doctorService.updateDoctor(doctor);
        if (status == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor updated"));
        } else if (status == -1) {
            return ResponseEntity.status(404).body(Map.of("error", "Doctor not found"));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "Some internal error occurred"));
        }
    }

    /**
     * ✅ 6. Delete Doctor
     */
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<?> deleteDoctor(
            @PathVariable long id,
            @PathVariable String token) {

        if (!service.validateToken(token, "admin")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid token!"));
        }

        int status = doctorService.deleteDoctor(id);
        if (status == 1) {
            return ResponseEntity.ok(Map.of("message", "Doctor deleted successfully"));
        } else if (status == -1) {
            return ResponseEntity.status(404).body(Map.of("error", "Doctor not found with id"));
        } else {
            return ResponseEntity.internalServerError().body(Map.of("error", "Some internal error occurred"));
        }
    }

    /**
     * ✅ 7. Filter Doctors
     */
    @GetMapping("/filter/{name}/{time}/{speciality}")
    public ResponseEntity<?> filterDoctors(
            @PathVariable String name,
            @PathVariable String time,
            @PathVariable String speciality) {

        return ResponseEntity.ok(service.filterDoctor(name, time, speciality));
    }

}
