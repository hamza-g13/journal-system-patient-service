package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Practitioner;
import se.kth.journalsystem.patient.model.enums.PractitionerType;
import java.util.List;
import java.util.Optional;

@Repository
public interface PractitionerRepository extends JpaRepository<Practitioner, Long> {

    List<Practitioner> findByType(PractitionerType type);

    Optional<Practitioner> findByUserId(String userId);
}
