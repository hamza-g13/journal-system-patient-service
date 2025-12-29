package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.organization.OrganizationRequest;
import se.kth.journalsystem.patient.dto.organization.OrganizationResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.exception.NotFoundException;
import se.kth.journalsystem.patient.model.Organization;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.repository.OrganizationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public OrganizationResponse createOrganization(OrganizationRequest request, UserResponse currentUser) {
        if (!authorizationService.canCreateOrganization(currentUser)) {
            throw new ForbiddenException("Only admins can create organizations");
        }

        Organization org = new Organization();
        org.setName(request.name());
        org.setType(request.type());
        org.setAddress(request.address());
        org.setPhoneNumber(request.phoneNumber());

        org = organizationRepository.save(org);

        return new OrganizationResponse(
                org.getId(),
                org.getName(),
                org.getType(),
                org.getAddress());
    }

    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll()
                .stream()
                .map(org -> new OrganizationResponse(
                        org.getId(),
                        org.getName(),
                        org.getType(),
                        org.getAddress()))
                .collect(Collectors.toList());
    }

    public Organization findById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organization not found"));
    }

    public OrganizationResponse getOrganizationById(Long id) {
        Organization org = findById(id);
        return new OrganizationResponse(
                org.getId(),
                org.getName(),
                org.getType(),
                org.getAddress());
    }
}


