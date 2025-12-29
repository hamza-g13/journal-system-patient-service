package se.kth.journalsystem.patient.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LocationRequest(
        @NotBlank(message = "Location name is required") @Size(min = 2, max = 100) String name,

        String address,

        String city,

        String postalCode,

        String country) {
}


