package se.kth.journalsystem.patient.dto.observation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ObservationRequest(
        @NotNull(message = "Patient ID is required") Long patientId,

        @NotBlank(message = "Observation type is required") @Size(max = 100, message = "Type must be less than 100 characters") String type,

        @NotBlank(message = "Observation value is required") String value,

        String unit,

        @Size(max = 1000, message = "Notes must be less than 1000 characters") String notes) {
}


