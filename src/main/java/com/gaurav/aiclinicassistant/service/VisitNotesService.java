package com.gaurav.aiclinicassistant.service;

import com.gaurav.aiclinicassistant.model.Appointment;
import com.gaurav.aiclinicassistant.model.Patient;
import com.gaurav.aiclinicassistant.model.VisitNotes;
import com.gaurav.aiclinicassistant.repository.AppointmentRepository;
import com.gaurav.aiclinicassistant.repository.PatientRepository;
import com.gaurav.aiclinicassistant.repository.VisitNotesRepository;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitNotesService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private VisitNotesRepository visitNotesRepository;
    private AppointmentRepository appointmentRepository;

    private PatientRepository patientRepository;

    @Autowired
    public VisitNotesService(VisitNotesRepository visitNotesRepository, AppointmentRepository appointmentRepository) {
        this.visitNotesRepository = visitNotesRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Tool
    public VisitNotes createVisitNotes(UUID appointmentId, String summary, String prescribedMeds, LocalDate followupDate) {
        log.info("Creating visit notes for appointment ID={}", appointmentId);

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found with ID: " + appointmentId);
        }

        VisitNotes notes = new VisitNotes(
                appointmentOptional.get(),
                summary,
                prescribedMeds,
                followupDate
        );

        VisitNotes savedNotes = visitNotesRepository.save(notes);
        log.info("Visit notes saved with ID={}", savedNotes.getId());
        return savedNotes;
    }


    public List<VisitNotes> getVisitNotesByPatientNameAndAppointmentId(String patientFirstName) {
        log.info("Fetching visit notes for patient name={}", patientFirstName);

        List<Patient> patientOptional = patientRepository.findByFirstName(patientFirstName);

        if (patientOptional.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with name: " + patientFirstName);
        }

        List<Appointment> appointmentOptional = appointmentRepository.findByPatientId(patientOptional.get(0).getId());

        if (appointmentOptional.isEmpty()) {
            throw new IllegalArgumentException("Patient not found with name: " + patientFirstName);
        }

        return visitNotesRepository.findByAppointmentId(appointmentOptional.get(0).getId());
    }
}
