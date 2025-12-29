package se.kth.journalsystem.patient.dto.practitioner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import se.kth.journalsystem.patient.model.enums.PractitionerType;

public record PractitionerRequest(
                @NotBlank(message = "First name is required") @Size(min = 2, max = 50) String firstName,

                @NotBlank(message = "Last name is required") @Size(min = 2, max = 50) String lastName,

                @NotNull(message = "Practitioner type is required") PractitionerType type,

                @NotBlank(message = "licenseNumber is required") String licenseNumber,

                @NotNull(message = "User ID is required") String userId,

                Long organizationId) {
}
