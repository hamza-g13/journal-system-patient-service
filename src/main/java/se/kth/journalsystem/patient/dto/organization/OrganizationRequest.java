package se.kth.journalsystem.patient.dto.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrganizationRequest(
        @NotBlank(message = "Organization name is required") @Size(min = 2, max = 100) String name,

        String type,

        String address,

        String phoneNumber) {
}


