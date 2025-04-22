package com.gaurav.aiclinicassistant.service;

import com.gaurav.aiclinicassistant.model.Patient;
import com.gaurav.aiclinicassistant.repository.PatientRepository;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private PatientRepository patientRepository;

    // CRUD - Get a patient by ID
    public Optional<Patient> getPatientById(UUID patientId) {
        return patientRepository.findById(patientId);
    }

    // CRUD - Save a new patient
    @Tool
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // Get patient by email
    @Tool
    public Patient findPatientByEmail(String email) {
        return patientRepository.findByEmail(email).orElse(null);
    }

    // Search patients by first and last name
    @Tool
    public List<Patient> findPatientByFirstOrLastName(String firstName, String lastName) {
        if (firstName == null && lastName == null) {
            return List.of();
        }
        if (firstName == null) {
            return patientRepository.findByLastName(lastName);
        }
        if (lastName == null) {
            return patientRepository.findByFirstName(firstName);
        }
        return patientRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    // Get all patients
    @Tool
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Update patient information
    public Patient updatePatient(UUID patientId, Patient updatedPatient) {
        Optional<Patient> existingPatientOpt = patientRepository.findById(patientId);
        if (existingPatientOpt.isPresent()) {
            Patient existingPatient = existingPatientOpt.get();
            existingPatient.setFirstName(updatedPatient.getFirstName());
            existingPatient.setLastName(updatedPatient.getLastName());
            existingPatient.setEmail(updatedPatient.getEmail());
            existingPatient.setPhone(updatedPatient.getPhone());
            return patientRepository.save(existingPatient);
        } else {
            log.warn("Patient with ID={} not found", patientId);
            return null;
        }
    }
}