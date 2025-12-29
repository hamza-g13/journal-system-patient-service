package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.encounter.EncounterRequest;
import se.kth.journalsystem.patient.dto.encounter.EncounterResponse;
import se.kth.journalsystem.patient.dto.location.LocationResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.exception.NotFoundException;
import se.kth.journalsystem.patient.model.Encounter;
import se.kth.journalsystem.patient.model.Location;
import se.kth.journalsystem.patient.model.enums.UserRole;
import se.kth.journalsystem.patient.model.Patient;
import se.kth.journalsystem.patient.model.Practitioner;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.repository.EncounterRepository;
import se.kth.journalsystem.patient.repository.PractitionerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncounterService {

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PractitionerRepository practitionerRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private AuthorizationService authorizationService;

    public EncounterResponse createEncounter(EncounterRequest request, UserResponse currentUserDTO) {
        if (!authorizationService.canCreateEncounter(currentUserDTO)) {
            throw new ForbiddenException("Only doctors and staff can create encounters");
        }

        // Hämta den inloggade användarens practitioner
        Practitioner currentPractitioner = practitionerRepository.findByUserId(currentUserDTO.getId())
                .orElseThrow(() -> new ForbiddenException("You must be a practitioner to create encounters"));

        Patient patient = patientService.findById(request.patientId());

        Encounter encounter = new Encounter();
        encounter.setPatient(patient);
        encounter.setPractitioner(currentPractitioner); // Använd ALLTID current user's practitioner
        encounter.setType(request.type());
        encounter.setNotes(request.notes());
        encounter.setReasonForVisit(request.reasonForVisit());

        if (request.locationId() != null) {
            Location location = locationService.findById(request.locationId());
            encounter.setLocation(location);
        }

        encounter = encounterRepository.save(encounter);
        return mapToResponse(encounter);
    }

    public List<EncounterResponse> getEncountersByPatientId(Long patientId, UserResponse currentUserDTO) {
        Patient patient = patientService.findById(patientId);

        if (!authorizationService.canReadEncounter(currentUserDTO, patient.getUserId())) {
            throw new ForbiddenException("You can only view your own encounters");
        }

        return encounterRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<EncounterResponse> getEncountersByPractitionerId(Long practitionerId, UserResponse currentUserDTO) {
        Practitioner practitioner = practitionerRepository.findById(practitionerId)
                .orElseThrow(() -> new NotFoundException("Practitioner not found"));

        if (currentUserDTO.getRole() == UserRole.ADMIN) {
            // Admin kan se allt
        } else if (currentUserDTO.getId().equals(practitioner.getUserId())) {
            // Läkaren ser sina egna encounters
        }
        else {
            throw new ForbiddenException("Access denied");
        }

        return encounterRepository.findByPractitionerId(practitionerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EncounterResponse getEncounterById(Long id, UserResponse currentUserDTO) {
        Encounter encounter = encounterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Encounter not found"));

        if (!authorizationService.canReadEncounter(currentUserDTO, encounter.getPatient().getUserId())) {
            throw new ForbiddenException("Access denied to this encounter");
        }

        return mapToResponse(encounter);
    }

    private EncounterResponse mapToResponse(Encounter encounter) {
        String patientName = encounter.getPatient().getFirstName() + " " +
                encounter.getPatient().getLastName();

        String practitionerName = encounter.getPractitioner().getFirstName() + " " +
                encounter.getPractitioner().getLastName();

        LocationResponse locationResponse = null;
        if (encounter.getLocation() != null) {
            Location loc = encounter.getLocation();
            locationResponse = new LocationResponse(
                    loc.getId(),
                    loc.getName(),
                    loc.getAddress(),
                    loc.getCity());
        }

        return new EncounterResponse(
                encounter.getId(),
                patientName,
                practitionerName,
                encounter.getEncounterDate(),
                encounter.getType(),
                encounter.getNotes(),
                encounter.getReasonForVisit(),
                locationResponse);
    }
}


