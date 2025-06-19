package com.project.back_end.repository;

import com.project.back_end.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // ✅ Custom finder to get Admin by username
    Admin findByUsername(String username);

}
