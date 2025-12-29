package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.condition.ConditionRequest;
import se.kth.journalsystem.patient.dto.condition.ConditionResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.model.Condition;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.repository.ConditionRepository;
import se.kth.journalsystem.patient.repository.PractitionerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConditionService {

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public ConditionResponse createCondition(ConditionRequest request, UserResponse currentUserDTO) {
        if (!authorizationService.canCreateCondition(currentUserDTO)) {
            throw new ForbiddenException("Only doctors and staff can create conditions");
        }

        Patient patient = patientService.findById(request.patientId());

        Condition condition = new Condition();
        condition.setPatient(patient);
        condition.setDiagnosis(request.diagnosis());
        condition.setDescription(request.description());
        condition.setStatus(request.status());
        condition.setSeverity(request.severity());

        practitionerRepository.findByUserId(currentUserDTO.getId())
                .ifPresent(condition::setDiagnosedBy);

        condition = conditionRepository.save(condition);
        return mapToResponse(condition);
    }

    public List<ConditionResponse> getConditionsByPatientId(Long patientId, UserResponse currentUserDTO) {
        Patient patient = patientService.findById(patientId);

        if (!authorizationService.canReadCondition(currentUserDTO, patient.getUserId())) {
            throw new ForbiddenException("You can only view your own conditions");
        }

        return conditionRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ConditionResponse mapToResponse(Condition condition) {
        String diagnosedByName = null;
        if (condition.getDiagnosedBy() != null) {
            diagnosedByName = condition.getDiagnosedBy().getFirstName() + " " +
                    condition.getDiagnosedBy().getLastName();
        }

        return new ConditionResponse(
                condition.getId(),
                condition.getDiagnosis(),
                condition.getDescription(),
                condition.getStatus(),
                condition.getSeverity(),
                condition.getDiagnosisDate(),
                diagnosedByName);
    }
}


