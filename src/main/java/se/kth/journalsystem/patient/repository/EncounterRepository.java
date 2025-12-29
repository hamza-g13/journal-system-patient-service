package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Encounter;
import java.util.List;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

    List<Encounter> findByPatientId(Long patientId);

    List<Encounter> findByPractitionerId(Long practitionerId);
}


