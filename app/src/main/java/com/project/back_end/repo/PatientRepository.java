package com.project.back_end.repo;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // ✅ 1. Find patient by email
    Optional<Patient> findByEmail(String email);

    // ✅ 2. Find patient by email OR phone number
    Optional<Patient> findByEmailOrPhone(String email, String phone);
}
