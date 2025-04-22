package com.gaurav.aiclinicassistant.service;

import com.gaurav.aiclinicassistant.model.Doctor;
import com.gaurav.aiclinicassistant.repository.DoctorRepository;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DoctorService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private DoctorRepository doctorRepository;

    // Static map for issue-to-specialty
    private static final Map<String, String> issueToSpecialtyMap = Map.of(
            "stomach pain", "Gastroenterology",
            "vision problems", "Ophthalmology",
            "back pain", "Orthopedics",
            "headache", "Neurology",
            "chest pain", "Cardiology",
            "cough", "Pulmonology",
            "fever", "General Medicine",
            "skin rash", "Dermatology",
            "joint pain", "Rheumatology"
    );

    // Recommending doctors based on the issue
    @Tool
    public List<Doctor> recommendDoctors(String issue) {
        String specialty = issueToSpecialtyMap.get(issue.toLowerCase());
        if (specialty != null) {
            return doctorRepository.findBySpecialty(specialty);
        } else {
            log.warn("No matching specialty found for issue='{}'", issue);
            return List.of();
        }
    }

    // Get doctors by specialty
    @Tool("Get doctors by specialty.")
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    // Get doctor schedule by first name
    @Tool("Get doctor schedule by first name.")
    public List<String> getDoctorSchedule(String firstName) {
        List<Doctor> doctors = doctorRepository.findByFirstName(firstName);
        return doctors.isEmpty() ? List.of() : doctors.get(0).getDaysAvailable();
    }

    // CRUD - Get a doctor by ID
    public Optional<Doctor> getDoctorById(UUID doctorId) {
        return doctorRepository.findById(doctorId);
    }

    // CRUD - Save a new doctor
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // CRUD - Get doctor by name
    @Tool("Get doctor by first name and/or last name.")
    public List<Doctor> getDoctorsByName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return doctorRepository.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            return doctorRepository.findByFirstName(firstName);
        } else if (lastName != null) {
            return doctorRepository.findByLastName(lastName);
        } else {
            return List.of();
        }
    }
}