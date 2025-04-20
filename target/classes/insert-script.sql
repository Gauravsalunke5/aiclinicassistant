-- Insert 3 doctors
INSERT INTO doctor (id, firstName, lastName, specialty, email, phone, days_available, created_at, updated_at)
VALUES
    (gen_random_uuid(), 'Alice', 'S', 'Cardiology', 'alice.s@hospital.com', '123-456-7890', 'Monday,Wednesday,Friday', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Bob', 'J', 'Dermatology', 'bob.j@hospital.com', '987-654-3210', 'Tuesday,Thursday', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Carol', 'W', 'Neurology', 'carol.w@hospital.com', '555-555-5555', 'Monday,Tuesday,Thursday', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert 1 patient
INSERT INTO patient (id, firstName, lastName, email, phone, dob, gender, created_at)
VALUES
    (gen_random_uuid(), 'David', 'B', 'david.b@example.com', '444-123-4567', '1990-08-15', 'Male', CURRENT_TIMESTAMP);
