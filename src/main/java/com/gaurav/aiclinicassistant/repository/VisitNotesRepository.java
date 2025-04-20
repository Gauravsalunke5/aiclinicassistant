package com.gaurav.aiclinicassistant.repository;

import com.gaurav.aiclinicassistant.model.VisitNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VisitNotesRepository extends JpaRepository<VisitNotes, UUID> {

    List<VisitNotes> findByAppointmentId(UUID appointmentId); // Find visit notes by appointment ID
}
