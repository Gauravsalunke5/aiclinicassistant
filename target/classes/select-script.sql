
-- =============================
-- 📋 SELECT Queries
-- =============================

-- 1️⃣ All Doctors and their Available Days
SELECT id, firstName, lastName, specialty, days_available
FROM doctor;

-- 2️⃣ All Patients
SELECT id, firstName, lastName, email, phone, gender, dob
FROM patient;

-- 3️⃣ All Appointments (with doctor and patient names)
SELECT 
    a.id AS appointment_id,
    d.firstName || ' ' || d.lastName AS doctor_name,
    p.firstName || ' ' || p.lastName AS patient_name,
    a.time_slot,
    a.status
FROM appointment a
JOIN doctor d ON a.doctor_id = d.id
JOIN patient p ON a.patient_id = p.id
ORDER BY a.time_slot;

-- 4️⃣ All Visit Notes (with appointment, doctor, and patient info)
SELECT 
    vn.id AS visit_note_id,
    a.time_slot,
    d.firstName || ' ' || d.lastName AS doctor_name,
    p.firstName || ' ' || p.lastName AS patient_name,
    vn.summary,
    vn.prescribed_meds,
    vn.followup_date
FROM visit_notes vn
JOIN appointment a ON vn.appointment_id = a.id
JOIN doctor d ON a.doctor_id = d.id
JOIN patient p ON a.patient_id = p.id
ORDER BY vn.created_at DESC;

-- 5️⃣ Appointments for Dr. Alice Smith
SELECT 
    a.id,
    a.time_slot,
    p.firstName || ' ' || p.lastName AS patient_name,
    a.status
FROM appointment a
JOIN doctor d ON a.doctor_id = d.id
JOIN patient p ON a.patient_id = p.id
WHERE d.email = 'alice.smith@hospital.com'
ORDER BY a.time_slot;