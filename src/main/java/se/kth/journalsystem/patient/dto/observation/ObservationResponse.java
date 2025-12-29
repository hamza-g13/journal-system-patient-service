package se.kth.journalsystem.patient.dto.observation;

import java.time.LocalDateTime;

public record ObservationResponse(
        Long id,
        String type,
        String value,
        String unit,
        String notes,
        LocalDateTime observationDate,
        String recordedByName) {
}


