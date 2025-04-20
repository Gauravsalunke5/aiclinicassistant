package com.gaurav.aiclinicassistant.repository;

import com.gaurav.aiclinicassistant.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    List<Doctor> findBySpecialty(String specialty); // Find doctor by specialty

    List<Doctor> findBySpecialtyContainingIgnoreCase(String specialty); // Find doctors by specialty with partial match


    List<Doctor> findByFirstName(String firstName); // Find doctor by first name

    List<Doctor> findByLastName(String lastName); // Find doctor by last name

    List<Doctor> findByFirstNameAndLastName(String firstName, String lastName); // Find doctor by first name and last name

    List<Doctor> findByFirstNameContainingIgnoreCase(String firstName); // Find doctors by first name with partial match

    List<Doctor> findByLastNameContainingIgnoreCase(String lastName); // Find doctors by last name with partial match

    List<Doctor> findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName); // Find doctors by first name and last name with partial match
}
