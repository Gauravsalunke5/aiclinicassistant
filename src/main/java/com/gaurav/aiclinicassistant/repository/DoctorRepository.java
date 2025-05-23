package com.gaurav.aiclinicassistant.repository;

import com.gaurav.aiclinicassistant.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    List<Doctor> findBySpecialty(String specialty);

    List<Doctor> findByFirstNameAndLastName(String firstName, String lastName);

    List<Doctor> findByFirstName(String firstName);

    List<Doctor> findByLastName(String lastName);

    Optional<Doctor> findById(UUID doctorId);
}