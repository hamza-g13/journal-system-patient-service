package se.kth.journalsystem.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.journalsystem.patient.model.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}


