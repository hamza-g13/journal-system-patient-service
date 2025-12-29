package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.patient.PatientRequest;
import se.kth.journalsystem.patient.dto.patient.PatientResponse;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.exception.NotFoundException;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.model.enums.UserRole;
import se.kth.journalsystem.patient.repository.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public PatientResponse createPatient(PatientRequest request, UserResponse currentUserDTO) {
        if (!authorizationService.canCreatePatient(currentUserDTO)) {
            throw new ForbiddenException("You don't have permission to create patients");
        }

        // In a real microservice, we would verify the user exists in Auth Service
        // For now, we assume the userId passed in the request is valid.
        // If the current user is a PATIENT, they can only create their own profile.

        if (patientRepository.findByUserId(request.userId()).isPresent()) {
            throw new RuntimeException("Patient already exists for this user");
        }

        Patient patient = new Patient();
        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setSocialSecurityNumber(request.socialSecurityNumber());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setAddress(request.address());
        patient.setUserId(request.userId());

        patient = patientRepository.save(patient);
        return mapToResponse(patient);
    }

    public PatientResponse getPatientById(Long id, UserResponse currentUserDTO) {
        Patient patient = findById(id);

        if (!authorizationService.canReadPatient(currentUserDTO, patient.getUserId())) {
            throw new ForbiddenException("You can only view your own patient data");
        }

        return mapToResponse(patient);
    }

    public List<PatientResponse> getAllPatients(UserResponse currentUserDTO) {
        if (currentUserDTO.getRole() == UserRole.PATIENT) {
            throw new ForbiddenException("Patients cannot view all patients");
        }

        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PatientResponse getPatientByUserId(String userId, UserResponse currentUserDTO) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        if (!authorizationService.canReadPatient(currentUserDTO, patient.getUserId())) {
            throw new ForbiddenException("Access denied");
        }

        return mapToResponse(patient);
    }

    public PatientResponse updatePatient(Long id, PatientRequest request, UserResponse currentUserDTO) {
        Patient patient = findById(id);

        if (!authorizationService.canUpdatePatient(currentUserDTO, patient.getUserId())) {
            throw new ForbiddenException("You don't have permission to update patient data");
        }

        patient.setFirstName(request.firstName());
        patient.setLastName(request.lastName());
        patient.setSocialSecurityNumber(request.socialSecurityNumber());
        patient.setDateOfBirth(request.dateOfBirth());
        patient.setPhoneNumber(request.phoneNumber());
        patient.setAddress(request.address());

        patient = patientRepository.save(patient);
        return mapToResponse(patient);
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found"));
    }

    private PatientResponse mapToResponse(Patient patient) {
        // In a real microservice, we might fetch username/role from Auth Service
        // or just return the ID. For now, we'll return a placeholder or just the ID.
        // To match the DTO, we need a UserResponse.
        // We don't have the username here unless we fetch it.
        // We'll set username to "Unknown" or similar, or just null if allowed.
        // Or we can change the DTO to just return userId.
        // But to keep frontend compatibility, we'll try to fill what we can.

        UserResponse userResponse = new UserResponse(
                patient.getUserId(),
                "Unknown", // We don't have username in Patient Service DB
                UserRole.PATIENT // We assume it's a patient
        );

        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getSocialSecurityNumber(),
                patient.getDateOfBirth(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                userResponse);
    }
}
