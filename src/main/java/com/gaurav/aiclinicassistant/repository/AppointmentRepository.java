package com.gaurav.aiclinicassistant.repository;

import com.gaurav.aiclinicassistant.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDoctorId(UUID doctorId); // Find all appointments for a doctor

    List<Appointment> findByPatientId(UUID patientId); // Find all appointments for a patient

    List<Appointment> findByStatus(String status); // Find appointments by status (e.g., booked, canceled)
}
