package com.project.back_end.repository;

import com.project.back_end.models.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends MongoRepository<Prescription, String> {

    // ✅ Find all prescriptions for a given appointment ID
    List<Prescription> findByAppointmentId(Long appointmentId);
}
