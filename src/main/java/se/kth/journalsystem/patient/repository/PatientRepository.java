package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Patient;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findBySocialSecurityNumber(String ssn);

    Optional<Patient> findByUserId(String userId);
}
