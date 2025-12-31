-- Insert Default Organization (optional but good practice)
-- INSERT IGNORE INTO organizations (id, name, address) VALUES (1, 'KTH Hospital', 'Stockholm');

-- Insert Doctor Profile
INSERT IGNORE INTO practitioners (first_name, last_name, type, license_number, user_id)
VALUES ('John', 'Doe', 'DOCTOR', 'DOC-12345', 'doctor-uuid-1234');

-- Insert Staff Profile
INSERT IGNORE INTO practitioners (first_name, last_name, type, license_number, user_id)
VALUES ('Jane', 'Smith', 'STAFF', 'STF-67890', 'staff-uuid-1234');

-- Insert Patient Profile
INSERT IGNORE INTO patients (first_name, last_name, social_security_number, date_of_birth, phone_number, address, user_id)
VALUES ('Alice', 'Wonderland', '19900101-1234', '1990-01-01', '070-1234567', 'Wonderland St 1', 'patient-uuid-1234');