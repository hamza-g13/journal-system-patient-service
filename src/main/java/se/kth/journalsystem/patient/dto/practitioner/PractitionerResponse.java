package se.kth.journalsystem.patient.dto.practitioner;

import se.kth.journalsystem.patient.dto.organization.OrganizationResponse;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.model.enums.PractitionerType;

public record PractitionerResponse(
        Long id,
        String firstName,
        String lastName,
        PractitionerType type,
        String licenseNumber,
        UserResponse user,
        OrganizationResponse organization) {
}


