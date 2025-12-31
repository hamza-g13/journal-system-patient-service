-- LÄKARE PROFIL
INSERT IGNORE INTO practitioners (first_name, last_name, type, license_number, user_id)
VALUES ('Dr. John', 'Doe', 'DOCTOR', 'DOC-123', 'doctor-uuid-1234');

-- SJUKSKÖTERSKA / STAFF PROFIL
INSERT IGNORE INTO practitioners (first_name, last_name, type, license_number, user_id)
VALUES ('Nurse Jane', 'Smith', 'STAFF', 'NUR-456', 'staff-uuid-1234');

-- PATIENT PROFIL
INSERT IGNORE INTO patients (first_name, last_name, social_security_number, date_of_birth, user_id)
VALUES ('Alice', 'Svensson', '19900101-1234', '1990-01-01', 'patient-uuid-1234');
