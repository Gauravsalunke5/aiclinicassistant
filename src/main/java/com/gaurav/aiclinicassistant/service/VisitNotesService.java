package com.gaurav.aiclinicassistant.service;

import com.gaurav.aiclinicassistant.model.Appointment;
import com.gaurav.aiclinicassistant.model.Doctor;
import com.gaurav.aiclinicassistant.model.Patient;
import com.gaurav.aiclinicassistant.model.VisitNotes;
import com.gaurav.aiclinicassistant.repository.AppointmentRepository;
import com.gaurav.aiclinicassistant.repository.PatientRepository;
import com.gaurav.aiclinicassistant.repository.VisitNotesRepository;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VisitNotesService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private VisitNotesRepository visitNotesRepository;
    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;


    private Patient findPatientByName(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + firstName + " " + lastName));
    }

    private Appointment findLatestAppointmentForPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .max(Comparator.comparing(Appointment::getTimeSlot)) // pick latest appointment
                .orElseThrow(() -> new IllegalArgumentException("No appointments found for patient ID: " + patientId));
    }

    @Tool("Add visit notes for a completed appointment by providing the patient's first and last name, summary, optionally prescribed medications, and optional follow-up date Provide the datetime in the format: dd-MM-yyyy HH:mm or empty")
    public VisitNotes createVisitNotes(String patientFirstName,
                                       String patientLastName, String summary, String prescribedMeds, String followupDate) {
        log.info("Creating visit notes for patient={}", patientFirstName);

        Patient patient = findPatientByName(patientFirstName, patientLastName);
        UUID appointmentId = findLatestAppointmentForPatient(patient.getId()).getId();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));


        VisitNotes notes;
        if (followupDate == null || followupDate.isBlank()) {
            notes = new VisitNotes(appointment, summary, prescribedMeds);

        } else {
            LocalDateTime followupDateTime = LocalDateTime.parse(followupDate, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            notes = new VisitNotes(appointment, summary, prescribedMeds, followupDateTime);
        }
        VisitNotes savedNotes = visitNotesRepository.save(notes);
        log.info("Saved visit notes with ID={}", savedNotes.getId());
        return savedNotes;
    }

    @Tool("Fetch latest visit notes for a patient by their first name and last name.")
    public VisitNotes getLatestVisitNotesByPatientName(String patientFirstName, String patientLastName) {
        log.info("Fetching latest visit notes for patient={}", patientFirstName);

        Patient patient = findPatientByName(patientFirstName, patientLastName);
        Appointment latestAppointment = findLatestAppointmentForPatient(patient.getId());

        return visitNotesRepository.findByAppointmentId(latestAppointment.getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No visit notes found for latest appointment."));
    }

    @Tool("List all visit notes for a patient using their first name and last name.")
    public List<VisitNotes> getAllVisitNotesByPatientName(String patientFirstName, String patientLastName) {
        log.info("Fetching all visit notes for patient={}", patientFirstName);

        Patient patient = findPatientByName(patientFirstName, patientLastName);
        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());

        List<VisitNotes> notes = new ArrayList<>();
        for (Appointment appointment : appointments) {
            notes.addAll(visitNotesRepository.findByAppointmentId(appointment.getId()));
        }
        return notes;
    }

    @Tool("Get visit notes for a specific appointment using the appointment ID.")
    public List<VisitNotes> getVisitNotesByAppointmentId(UUID appointmentId) {
        return visitNotesRepository.findByAppointmentId(appointmentId);
    }
}