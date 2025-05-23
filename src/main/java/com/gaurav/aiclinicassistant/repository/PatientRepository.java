package com.gaurav.aiclinicassistant.repository;

import com.gaurav.aiclinicassistant.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByEmail(String email);

    List<Patient> findByFirstName(String firstName);

    List<Patient> findByLastName(String lastName);

    List<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}