package se.kth.journalsystem.patient.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.kth.journalsystem.patient.dto.patient.PatientRequest;
import se.kth.journalsystem.patient.dto.patient.PatientResponse;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.model.enums.UserRole;
import se.kth.journalsystem.patient.repository.PatientRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private PatientService patientService;

    @Test
    void createPatient_ShouldSucceed_WhenAuthorized() {
        // Arrange
        UserResponse adminUser = new UserResponse("admin-1", "admin", UserRole.ADMIN);
        PatientRequest request = new PatientRequest(
                "Anna", "Andersson", "19900101-1234",
                LocalDate.of(1990, 1, 1), "0701234567", "Storgatan 1", "user-123"
        );

        when(authorizationService.canCreatePatient(adminUser)).thenReturn(true);
        when(patientRepository.findByUserId("user-123")).thenReturn(Optional.empty());

        Patient savedPatient = new Patient();
        savedPatient.setId(1L);
        savedPatient.setFirstName("Anna");
        savedPatient.setUserId("user-123");

        when(patientRepository.save(any(Patient.class))).thenReturn(savedPatient);

        // Act
        PatientResponse response = patientService.createPatient(request, adminUser);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Anna", response.firstName());
        verify(patientRepository).save(any(Patient.class));
    }
}
