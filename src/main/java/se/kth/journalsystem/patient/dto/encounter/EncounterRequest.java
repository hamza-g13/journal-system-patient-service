package se.kth.journalsystem.patient.dto.encounter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import se.kth.journalsystem.patient.model.enums.EncounterType;

public record EncounterRequest(
        @NotNull(message = "Patient ID is required") Long patientId,

        @NotNull(message = "Practitioner ID is required") Long practitionerId,

        @NotNull(message = "Encounter type is required") EncounterType type,

        @Size(max = 2000, message = "Notes must be less than 2000 characters") String notes,

        String reasonForVisit,

        Long locationId) {
}


