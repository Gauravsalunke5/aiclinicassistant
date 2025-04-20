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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    private DoctorRepository doctorRepository;

    private PatientRepository patientRepository;

    // Create a new appointment
    @Tool
    public Appointment createAppointment(Appointment appointment) {
        // AI-powered logic to create an appointment based on availability and patient needs
        return appointmentRepository.save(appointment);
    }


    // Create a new appointment
    @Tool("Create a new appointment by patient name and doctor name")
    public Appointment createAppointment(String patientFirstName, String patientLastName, String doctorFirstName, String doctorLastName, LocalDateTime timeSlot) {
        // Get doctor and patient objects
        List<Doctor> doctor = doctorRepository.findByFirstNameAndLastName(doctorFirstName, doctorLastName);
        List<Patient> patient = patientRepository.findByFirstNameAndLastName(patientFirstName, patientLastName);

        if (doctor == null || doctor.isEmpty()) {
            throw new IllegalArgumentException("No doctor found with name: " + doctorFirstName + " " + doctorLastName);
        }

        if (patient == null || patient.isEmpty()) {
            throw new IllegalArgumentException("No patient found with name: " + patientFirstName + " " + patientLastName);
        }

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor.get(0));
        appointment.setPatient(patient.get(0));
        appointment.setTimeSlot(timeSlot);
        appointment.setStatus(AppointmentStatus.BOOKED);

        return createAppointment(appointment);
    }

    // Get appointments for a specific doctor
    @Tool
    public List<Appointment> getAppointmentsForDoctor(UUID doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Get appointments for a specific patient
    @Tool
    public List<Appointment> getAppointmentsForPatient(UUID patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Update appointment status (e.g., from 'BOOKED' to 'CANCELLED')
    public Appointment updateAppointmentStatus(UUID appointmentId, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus(status); // Ensure Appointment has setStatus() method
        return appointmentRepository.save(appointment);
    }

    // Cancel an appointment (directly delete)
    @Tool
    public void cancelAppointment(UUID appointmentId) {
        // Fetch the appointment details
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();

        // Directly delete the appointment (no approval needed)
        appointmentRepository.deleteById(appointmentId);
        System.out.println("Appointment " + appointmentId + " cancelled successfully");
    }


    // Get appointments for a specific doctor by doctor name
    @Tool
    public List<Appointment> getAppointmentsForDoctorByName(String doctorFirstName, String doctorLastName) {
        // Fetch the doctor record by doctor name
        List<Doctor> doctor = doctorRepository.findByFirstNameAndLastName(doctorFirstName, doctorLastName);

        if (doctor == null) {
            throw new IllegalArgumentException("No doctor found with name: " + doctorFirstName + " " + doctorLastName);
        }
        // Get appointments for the doctor by doctor ID
        return appointmentRepository.findByDoctorId(doctor.get(0).getId());
    }

    // Get appointments for a specific patient by patient name
    @Tool
    public List<Appointment> getAppointmentsForPatientByName(String patientFirstName, String patientLastName) {
        // Fetch the patient record by patient name
        List<Patient> patient = patientRepository.findByFirstNameAndLastName(patientFirstName, patientLastName);

        if (patient == null) {
            throw new IllegalArgumentException("No patient found with name: " + patientFirstName + " " + patientLastName);
        }
        // Get appointments for the patient by patient ID
        return appointmentRepository.findByPatientId(patient.get(0).getId());
    }

}
