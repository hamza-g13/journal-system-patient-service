package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.observation.ObservationRequest;
import se.kth.journalsystem.patient.dto.observation.ObservationResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.model.Observation;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.repository.ObservationRepository;
import se.kth.journalsystem.patient.repository.PractitionerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private AuthorizationService authorizationService;

    public ObservationResponse createObservation(ObservationRequest request, UserResponse currentUserDTO) {
        if (!authorizationService.canCreateObservation(currentUserDTO)) {
            throw new ForbiddenException("Only doctors and staff can create observations");
        }

        Patient patient = patientService.findById(request.patientId());

        Observation observation = new Observation();
        observation.setPatient(patient);
        observation.setType(request.type());
        observation.setValue(request.value());
        observation.setUnit(request.unit());
        observation.setNotes(request.notes());

        practitionerRepository.findByUserId(currentUserDTO.getId())
                .ifPresent(observation::setRecordedBy);

        observation = observationRepository.save(observation);
        return mapToResponse(observation);
    }

    public List<ObservationResponse> getObservationsByPatientId(Long patientId, UserResponse currentUserDTO) {
        Patient patient = patientService.findById(patientId);

        if (!authorizationService.canReadObservation(currentUserDTO, patient.getUserId())) {
            throw new ForbiddenException("You can only view your own observations");
        }

        return observationRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ObservationResponse mapToResponse(Observation observation) {
        String recordedByName = null;
        if (observation.getRecordedBy() != null) {
            recordedByName = observation.getRecordedBy().getFirstName() + " " +
                    observation.getRecordedBy().getLastName();
        }

        return new ObservationResponse(
                observation.getId(),
                observation.getType(),
                observation.getValue(),
                observation.getUnit(),
                observation.getNotes(),
                observation.getObservationDate(),
                recordedByName);
    }
}


