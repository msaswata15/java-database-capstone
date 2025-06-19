package com.project.back_end.repository;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // ✅ 1. Find patient by email
    Patient findByEmail(String email);

    // ✅ 2. Find patient by email OR phone number
    Patient findByEmailOrPhone(String email, String phone);
}
