package se.kth.journalsystem.patient.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record PatientRequest(
        @NotBlank(message = "First name is required") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String firstName,

        @NotBlank(message = "Last name is required") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String lastName,

        @NotBlank(message = "Social security number is required") @Pattern(regexp = "^(\\d{12}|\\d{8}-\\d{4})$", message = "Social security number must be 12 digits or YYYYMMDD-XXXX") String socialSecurityNumber,

        @NotNull(message = "Date of birth is required") LocalDate dateOfBirth,

        @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Invalid phone number format") String phoneNumber,

        String address,

        @NotNull(message = "User ID is required") String userId) {
}
