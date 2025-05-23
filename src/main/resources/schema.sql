-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ================================
-- 🚨 DROP TABLES (in reverse order)
-- ================================
DROP TABLE IF EXISTS visit_notes CASCADE;
DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS patient CASCADE;
DROP TABLE IF EXISTS doctor CASCADE;

-- =============================
-- 🚨 DROP ENUM TYPE
-- =============================
DROP TYPE IF EXISTS appointment_status;


-- Doctor Table
CREATE TABLE doctor (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20),
    is_active BOOLEAN DEFAULT TRUE,
    days_available TEXT,  -- comma-separated string like 'Monday,Tuesday,Friday'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Patient Table
CREATE TABLE patient (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    phone VARCHAR(20),
    dob DATE,
    gender VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Appointment Table
CREATE TABLE appointment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    doctor_id UUID REFERENCES doctor(id) ON DELETE CASCADE,
    patient_id UUID REFERENCES patient(id) ON DELETE CASCADE,
    time_slot TIMESTAMP NOT NULL,
    status VARCHAR(20) DEFAULT 'BOOKED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Visit Notes Table
CREATE TABLE visit_notes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    appointment_id UUID REFERENCES appointment(id) ON DELETE CASCADE,
    summary TEXT,
    prescribed_meds VARCHAR(250),
    followup_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
