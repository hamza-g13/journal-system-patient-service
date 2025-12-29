package se.kth.journalsystem.patient.dto.organization;

public record OrganizationResponse(
        Long id,
        String name,
        String type,
        String address) {
}


