package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.organization.OrganizationResponse;
import se.kth.journalsystem.patient.dto.practitioner.PractitionerRequest;
import se.kth.journalsystem.patient.dto.practitioner.PractitionerResponse;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.exception.NotFoundException;
import se.kth.journalsystem.patient.model.Organization;
import se.kth.journalsystem.patient.model.Practitioner;
import se.kth.journalsystem.patient.model.enums.PractitionerType;
import se.kth.journalsystem.patient.model.enums.UserRole;
import se.kth.journalsystem.patient.repository.PractitionerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PractitionerService {

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private AuthorizationService authorizationService;

    public PractitionerResponse createPractitioner(PractitionerRequest request, UserResponse currentUserDTO) {
        if (!authorizationService.canCreatePractitioner(currentUserDTO)) {
            throw new ForbiddenException("Only admins can create practitioners");
        }

        if (practitionerRepository.findByUserId(request.userId()).isPresent()) {
            throw new RuntimeException("Practitioner already exists for this user");
        }

        Practitioner practitioner = new Practitioner();
        practitioner.setFirstName(request.firstName());
        practitioner.setLastName(request.lastName());
        practitioner.setType(request.type());
        practitioner.setLicenseNumber(request.licenseNumber());
        practitioner.setUserId(request.userId());

        if (request.organizationId() != null) {
            Organization organization = organizationService.findById(request.organizationId());
            practitioner.setOrganization(organization);
        }

        practitioner = practitionerRepository.save(practitioner);
        return mapToResponse(practitioner);
    }

    public List<PractitionerResponse> getAllPractitioners() {
        return practitionerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PractitionerResponse> getPractitionersByType(PractitionerType type) {
        return practitionerRepository.findByType(type)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PractitionerResponse getPractitionerById(Long id) {
        Practitioner practitioner = findById(id);
        return mapToResponse(practitioner);
    }

    public Practitioner findById(Long id) {
        return practitionerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Practitioner not found"));
    }

    public PractitionerResponse getPractitionerByUserId(String userId) {
        Practitioner practitioner = practitionerRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Practitioner not found for this user"));
        return mapToResponse(practitioner);
    }

    private PractitionerResponse mapToResponse(Practitioner practitioner) {
        // Similar to PatientService, we don't have username/role here.
        // We'll use placeholders.
        UserRole role = (practitioner.getType() == PractitionerType.DOCTOR) ? UserRole.DOCTOR : UserRole.STAFF;

        UserResponse userResponse = new UserResponse(
                practitioner.getUserId(),
                "Unknown",
                role);

        OrganizationResponse organizationResponse = null;
        if (practitioner.getOrganization() != null) {
            organizationResponse = new OrganizationResponse(
                    practitioner.getOrganization().getId(),
                    practitioner.getOrganization().getName(),
                    practitioner.getOrganization().getType(),
                    practitioner.getOrganization().getAddress());
        }

        return new PractitionerResponse(
                practitioner.getId(),
                practitioner.getFirstName(),
                practitioner.getLastName(),
                practitioner.getType(),
                practitioner.getLicenseNumber(),
                userResponse,
                organizationResponse);
    }
}
