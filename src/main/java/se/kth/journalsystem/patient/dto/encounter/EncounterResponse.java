package se.kth.journalsystem.patient.dto.encounter;

import se.kth.journalsystem.patient.dto.location.LocationResponse;
import se.kth.journalsystem.patient.model.enums.EncounterType;
import java.time.LocalDateTime;

public record EncounterResponse(
        Long id,
        String patientName,
        String practitionerName,
        LocalDateTime encounterDate,
        EncounterType type,
        String notes,
        String reasonForVisit,
        LocationResponse location) {
}


