package com.gaurav.aiclinicassistant.service;

import com.gaurav.aiclinicassistant.model.Appointment;
import com.gaurav.aiclinicassistant.model.AppointmentStatus;
import com.gaurav.aiclinicassistant.model.Doctor;
import com.gaurav.aiclinicassistant.model.Patient;
import com.gaurav.aiclinicassistant.repository.AppointmentRepository;
import com.gaurav.aiclinicassistant.repository.DoctorRepository;
import com.gaurav.aiclinicassistant.repository.PatientRepository;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppointmentService {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AppointmentRepository appointmentRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;

    // Used internally by multiple tools
    private Doctor findDoctorByName(String firstName, String lastName) {
        return doctorRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found: " + firstName + " " + lastName));
    }

    private Patient findPatientByName(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName, lastName)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + firstName + " " + lastName));
    }

    @Tool("Create a new appointment with a doctor for a patient at a specific time. Provide the datetime in the format: dd-MM-yyyy HH:mm")
    public Appointment createAppointmentByNames(
            String patientFirstName,
            String patientLastName,
            String doctorFirstName,
            String doctorLastName,
            String timeSlot  // Now it's a plain string
    ) {
        LocalDateTime parsedSlot = LocalDateTime.parse(timeSlot, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

        Doctor doctor = findDoctorByName(doctorFirstName, doctorLastName);
        Patient patient = findPatientByName(patientFirstName, patientLastName);

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setTimeSlot(parsedSlot);
        appointment.setStatus(AppointmentStatus.BOOKED.name());

        log.info("Creating appointment for {} with Dr. {} at {}", patient.getFirstName(), doctor.getLastName(), parsedSlot);
        return appointmentRepository.save(appointment);
    }


    @Tool("Get upcoming appointments for a doctor by full name.")
    public List<Appointment> getDoctorAppointments(String doctorFirstName, String doctorLastName) {
        Doctor doctor = findDoctorByName(doctorFirstName, doctorLastName);
        return appointmentRepository.findByDoctorId(doctor.getId());
    }

    @Tool("Get all appointments for a patient by full name.")
    public List<Appointment> getPatientAppointments(String patientFirstName, String patientLastName) {
        Patient patient = findPatientByName(patientFirstName, patientLastName);
        return appointmentRepository.findByPatientId(patient.getId());
    }

    @Tool("Cancel a scheduled appointment by its ID.")
    public void cancelAppointmentById(UUID appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IllegalArgumentException("Appointment not found: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
        log.info("Cancelled appointment {}", appointmentId);
    }

    @Tool("Update the status of an appointment using its ID.")
    public Appointment updateAppointmentStatus(UUID appointmentId, AppointmentStatus newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + appointmentId));

        appointment.setStatus(newStatus.name());
        log.info("Updated appointment {} to status {}", appointmentId, newStatus);
        return appointmentRepository.save(appointment);
    }

    @Tool("List all appointments scheduled with a specific doctor by their UUID.")
    public List<Appointment> listAppointmentsForDoctorId(UUID doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Tool("List all appointments scheduled for a patient using their UUID.")
    public List<Appointment> listAppointmentsForPatientId(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }
}