package se.kth.journalsystem.patient.dto.condition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import se.kth.journalsystem.patient.model.enums.ConditionStatus;

public record ConditionRequest(
        @NotNull(message = "Patient ID is required") Long patientId,

        @NotBlank(message = "Diagnosis is required") @Size(min = 2, max = 200, message = "Diagnosis must be between 2 and 200 characters") String diagnosis,

        @Size(max = 2000, message = "Description must be less than 2000 characters") String description,

        @NotNull(message = "Status is required") ConditionStatus status,

        String severity) {
}


