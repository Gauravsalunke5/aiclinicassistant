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

    public Optional<Patient> getPatientById(UUID patientId) {
        log.info("Fetching patient with ID={}", patientId);
        return patientRepository.findById(patientId);
    }

    @Tool
    public Patient savePatient(Patient patient) {
        log.info("Saving new patient with email={}", patient.getEmail());
        return patientRepository.save(patient);
    }

    public Patient findPatientByEmail(String email) {
        log.info("Searching for patient with email={}", email);
        return patientRepository.findByEmail(email).orElse(null);
    }

    public List<Patient> findPatientByFirstOrLastName(String firstName, String lastName) {
        log.info("Searching for patient with first name={}, last name={}", firstName, lastName);
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
}
