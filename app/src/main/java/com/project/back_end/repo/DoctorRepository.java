package com.project.back_end.repo;

import com.project.back_end.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // ✅ 1. Find by email
    Optional<Doctor> findByEmail(String email);

    // ✅ 2. Find by partial name match (LIKE, flexible)
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameLike(String name);

    // ✅ 3. Filter by partial name + exact specialty (both case-insensitive)
    @Query("SELECT d FROM Doctor d " +
            "WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND LOWER(d.specialty) = LOWER(:specialty)")
    List<Doctor> findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(String name, String specialty);

    // ✅ 4. Find all doctors by specialty (case-insensitive)
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
}
