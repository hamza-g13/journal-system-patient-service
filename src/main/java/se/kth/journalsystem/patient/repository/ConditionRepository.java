package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Condition;
import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {

    List<Condition> findByPatientId(Long patientId);
}


