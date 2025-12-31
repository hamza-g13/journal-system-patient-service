package se.kth.journalsystem.patient.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.model.Practitioner;
import se.kth.journalsystem.patient.model.enums.PractitionerType;
import se.kth.journalsystem.patient.repository.PatientRepository;
import se.kth.journalsystem.patient.repository.PractitionerRepository;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void run(String... args) {
        initDoctor();
        initStaff();
        initPatient();
    }

    // Using REQUIRES_NEW to ensure independent transactions, so one failure doesn't
    // rollback everything or crash startup
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initDoctor() {
        try {
            String userId = "doctor-uuid-1234";
            String license = "DOC-12345";

            if (practitionerRepository.findByUserId(userId).isPresent()) {
                logger.info("Doctor profile already exists (by userId).");
                return;
            }

            Optional<Practitioner> existing = practitionerRepository.findByLicenseNumber(license);
            if (existing.isPresent()) {
                logger.info("Doctor profile exists by license number but missing/wrong userId. Updating userId.");
                Practitioner p = existing.get();
                p.setUserId(userId);
                practitionerRepository.save(p);
            } else {
                Practitioner doctor = new Practitioner();
                doctor.setFirstName("John");
                doctor.setLastName("Doe");
                doctor.setType(PractitionerType.DOCTOR);
                doctor.setLicenseNumber(license);
                doctor.setUserId(userId);
                practitionerRepository.save(doctor);
                logger.info("Initialized default Doctor profile.");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Doctor profile: " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initStaff() {
        try {
            String userId = "staff-uuid-1234";
            String license = "STF-67890";

            if (practitionerRepository.findByUserId(userId).isPresent()) {
                logger.info("Staff profile already exists (by userId).");
                return;
            }

            Optional<Practitioner> existing = practitionerRepository.findByLicenseNumber(license);
            if (existing.isPresent()) {
                logger.info("Staff profile exists by license number but missing/wrong userId. Updating userId.");
                Practitioner p = existing.get();
                p.setUserId(userId);
                practitionerRepository.save(p);
            } else {
                Practitioner staff = new Practitioner();
                staff.setFirstName("Jane");
                staff.setLastName("Smith");
                staff.setType(PractitionerType.STAFF);
                staff.setLicenseNumber(license);
                staff.setUserId(userId);
                practitionerRepository.save(staff);
                logger.info("Initialized default Staff profile.");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Staff profile: " + e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void initPatient() {
        try {
            String userId = "patient-uuid-1234";
            String ssn = "19900101-1234";

            if (patientRepository.findByUserId(userId).isPresent()) {
                logger.info("Patient profile already exists (by userId).");
                return;
            }

            Optional<Patient> existing = patientRepository.findBySocialSecurityNumber(ssn);
            if (existing.isPresent()) {
                logger.info("Patient profile exists by SSN but missing/wrong userId. Updating userId.");
                Patient p = existing.get();
                p.setUserId(userId);
                patientRepository.save(p);
            } else {
                Patient patient = new Patient();
                patient.setFirstName("Alice");
                patient.setLastName("Wonderland");
                patient.setSocialSecurityNumber(ssn);
                patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
                patient.setPhoneNumber("070-1234567");
                patient.setAddress("Wonderland St 1");
                patient.setUserId(userId);
                patientRepository.save(patient);
                logger.info("Initialized default Patient profile.");
            }
        } catch (Exception e) {
            logger.error("Failed to initialize Patient profile: " + e.getMessage());
        }
    }
}
