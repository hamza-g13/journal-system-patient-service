package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Observation;
import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findByPatientId(Long patientId);

}


