package se.kth.journalsystem.patient.dto.location;

public record LocationResponse(
        Long id,
        String name,
        String address,
        String city) {
}


