package se.kth.journalsystem.patient.service;

import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.model.enums.UserRole;

@Service
public class AuthorizationService {

    // === PRACTITIONERS ===
    public boolean canCreatePractitioner(UserResponse currentUserResponse) {
        return currentUserResponse.getRole() == UserRole.ADMIN;
    }

    // === LOCATIONS ===
    public boolean canCreateLocation(UserResponse currentUserResponse) {
        return currentUserResponse.getRole() == UserRole.ADMIN ||
                currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF;
    }

    // === ORGANIZATIONS ===
    public boolean canCreateOrganization(UserResponse currentUser) {
        return currentUser.getRole() == UserRole.ADMIN;
    }

    // === PATIENTS ===
    public boolean canCreatePatient(UserResponse currentUserResponse) {
        return currentUserResponse.getRole() == UserRole.ADMIN;
    }

    // Patient kan bara läsa sin egen info
    public boolean canReadPatient(UserResponse currentUserResponse, String patientUserResponseId) {
        if (currentUserResponse.getRole() == UserRole.PATIENT) {
            return currentUserResponse.getId().equals(patientUserResponseId);
        }
        // Doctor, Staff, Admin kan läsa alla
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF ||
                currentUserResponse.getRole() == UserRole.ADMIN;
    }

    public boolean canUpdatePatient(UserResponse currentUserResponse, String patientUserResponseId) {
        return currentUserResponse.getRole() == UserRole.ADMIN ||
                currentUserResponse.getId().equals(patientUserResponseId);
    }

    // === OBSERVATIONS ===
    public boolean canCreateObservation(UserResponse currentUserResponse) {
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF;
    }

    // Patient kan bara läsa sin egen observations
    public boolean canReadObservation(UserResponse currentUserResponse, String patientUserResponseId) {
        if (currentUserResponse.getRole() == UserRole.PATIENT) {
            return currentUserResponse.getId().equals(patientUserResponseId);
        }
        // Doctor, Staff, Admin kan läsa alla
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF ||
                currentUserResponse.getRole() == UserRole.ADMIN;
    }

    // === ENCOUNTERS ===
    public boolean canCreateEncounter(UserResponse currentUserResponse) {
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF;
    }

    public boolean canReadEncounter(UserResponse currentUserResponse, String patientUserResponseId) {
        if (currentUserResponse.getRole() == UserRole.PATIENT) {
            return currentUserResponse.getId().equals(patientUserResponseId);
        }
        // Doctor, Staff, Admin kan läsa alla
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF ||
                currentUserResponse.getRole() == UserRole.ADMIN;
    }

    // === CONDITIONS ===
    public boolean canCreateCondition(UserResponse currentUserResponse) {
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF;
    }

    // Patient kan bara läsa sin egna conditions
    public boolean canReadCondition(UserResponse currentUserResponse, String patientUserResponseId) {
        if (currentUserResponse.getRole() == UserRole.PATIENT) {
            return currentUserResponse.getId().equals(patientUserResponseId);
        }
        // Doctor, Staff, Admin kan läsa alla
        return currentUserResponse.getRole() == UserRole.DOCTOR ||
                currentUserResponse.getRole() == UserRole.STAFF ||
                currentUserResponse.getRole() == UserRole.ADMIN;
    }
}
