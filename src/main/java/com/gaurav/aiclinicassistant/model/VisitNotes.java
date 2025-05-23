package com.gaurav.aiclinicassistant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "visit_notes")
public class VisitNotes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    private String summary;

    @Column(name = "prescribed_meds")
    private String prescribedMeds;  // You can store as a String or JSON format

    @Column(name = "followup_date")
    private LocalDateTime followUpDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    public VisitNotes(Appointment appointment, String summary, String prescribedMeds, LocalDateTime followUpDate) {
        this.appointment = appointment;
        this.summary = summary;
        this.prescribedMeds = prescribedMeds;
        this.followUpDate = followUpDate;
    }

    public VisitNotes(Appointment appointment, String summary, String prescribedMeds) {
        this.appointment = appointment;
        this.summary = summary;
        this.prescribedMeds = prescribedMeds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrescribedMeds() {
        return prescribedMeds;
    }

    public void setPrescribedMeds(String prescribedMeds) {
        this.prescribedMeds = prescribedMeds;
    }

    public LocalDateTime getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(LocalDateTime followUpDate) {
        this.followUpDate = followUpDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
