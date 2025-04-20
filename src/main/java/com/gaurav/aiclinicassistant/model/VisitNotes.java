package com.gaurav.aiclinicassistant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
 @NoArgsConstructor
@AllArgsConstructor
public class VisitNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    private String summary;
    private String prescribedMeds;  // You can store as a String or JSON format

    private LocalDate followUpDate;

    private LocalDateTime createdAt;

    public VisitNotes(Appointment appointment, String summary, String prescribedMeds, LocalDate followUpDate) {
        this.appointment = appointment;
        this.summary = summary;
        this.prescribedMeds = prescribedMeds;
        this.followUpDate = followUpDate;
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

    public LocalDate getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        this.followUpDate = followUpDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
