package se.kth.journalsystem.patient.dto.patient;

import se.kth.journalsystem.patient.dto.user.UserResponse;

import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String firstName,
        String lastName,
        String socialSecurityNumber,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address,
        UserResponse user) {
}


