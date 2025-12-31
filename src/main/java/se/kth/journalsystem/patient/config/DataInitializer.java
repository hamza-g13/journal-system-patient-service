package se.kth.journalsystem.patient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.model.Practitioner;
import se.kth.journalsystem.patient.model.enums.PractitionerType;
import se.kth.journalsystem.patient.repository.PatientRepository;
import se.kth.journalsystem.patient.repository.PractitionerRepository;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create Doctor Profile if it doesn't exist
        String doctorUserId = "doctor-uuid-1234";
        if (practitionerRepository.findByUserId(doctorUserId).isEmpty()) {
            Practitioner doctor = new Practitioner();
            doctor.setFirstName("John");
            doctor.setLastName("Doe");
            doctor.setType(PractitionerType.DOCTOR);
            doctor.setLicenseNumber("DOC-12345");
            doctor.setUserId(doctorUserId);
            practitionerRepository.save(doctor);
            System.out.println("Initialized default Doctor profile.");
        }

        // Create Staff Profile if it doesn't exist
        String staffUserId = "staff-uuid-1234";
        if (practitionerRepository.findByUserId(staffUserId).isEmpty()) {
            Practitioner staff = new Practitioner();
            staff.setFirstName("Jane");
            staff.setLastName("Smith");
            staff.setType(PractitionerType.STAFF);
            staff.setLicenseNumber("STF-67890");
            staff.setUserId(staffUserId);
            practitionerRepository.save(staff);
            System.out.println("Initialized default Staff profile.");
        }

        // Create Patient Profile if it doesn't exist
        String patientUserId = "patient-uuid-1234";
        if (patientRepository.findByUserId(patientUserId).isEmpty()) {
            Patient patient = new Patient();
            patient.setFirstName("Alice");
            patient.setLastName("Wonderland");
            patient.setSocialSecurityNumber("19900101-1234");
            patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
            patient.setPhoneNumber("070-1234567");
            patient.setAddress("Wonderland St 1");
            patient.setUserId(patientUserId);
            patientRepository.save(patient);
            System.out.println("Initialized default Patient profile.");
        }
    }
}
