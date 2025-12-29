package se.kth.journalsystem.patient.dto.condition;

import se.kth.journalsystem.patient.model.enums.ConditionStatus;
import java.time.LocalDateTime;

public record ConditionResponse(
        Long id,
        String diagnosis,
        String description,
        ConditionStatus status,
        String severity,
        LocalDateTime diagnosisDate,
        String diagnosedByName) {
}


