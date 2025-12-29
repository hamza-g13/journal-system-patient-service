package se.kth.journalsystem.patient.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import se.kth.journalsystem.patient.dto.condition.ConditionRequest;
import se.kth.journalsystem.patient.dto.condition.ConditionResponse;
import se.kth.journalsystem.patient.dto.encounter.EncounterRequest;
import se.kth.journalsystem.patient.dto.encounter.EncounterResponse;
import se.kth.journalsystem.patient.dto.location.LocationRequest;
import se.kth.journalsystem.patient.dto.location.LocationResponse;
import se.kth.journalsystem.patient.dto.observation.ObservationRequest;
import se.kth.journalsystem.patient.dto.observation.ObservationResponse;
import se.kth.journalsystem.patient.dto.organization.OrganizationRequest;
import se.kth.journalsystem.patient.dto.organization.OrganizationResponse;
import se.kth.journalsystem.patient.dto.patient.PatientRequest;
import se.kth.journalsystem.patient.dto.patient.PatientResponse;
import se.kth.journalsystem.patient.dto.practitioner.PractitionerRequest;
import se.kth.journalsystem.patient.dto.practitioner.PractitionerResponse;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.model.enums.PractitionerType;
import se.kth.journalsystem.patient.service.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientServiceController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PractitionerService practitionerService;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    private EncounterService encounterService;

    @Autowired
    private ObservationService observationService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private OrganizationService organizationService;

    private UserResponse getUser(org.springframework.security.oauth2.jwt.Jwt jwt) {
        return se.kth.journalsystem.patient.util.SecurityUtils.getUserFromJwt(jwt);
    }

    // ========== PATIENT ENDPOINTS ==========
    @PostMapping("/patients")
    public ResponseEntity<PatientResponse> createPatient(
            @Valid @RequestBody PatientRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(patientService.createPatient(request, getUser(jwt)));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponse>> getAllPatients(
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(patientService.getAllPatients(getUser(jwt)));
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientResponse> getPatientById(
            @PathVariable Long id,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(patientService.getPatientById(id, getUser(jwt)));
    }

    @GetMapping("/patients/me")
    public ResponseEntity<PatientResponse> getCurrentPatientInfo(
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        UserResponse user = getUser(jwt);
        return ResponseEntity.ok(patientService.getPatientByUserId(user.getId(), user));
    }

    @GetMapping("/patients/user/{userId}")
    public ResponseEntity<PatientResponse> getPatientByUserId(
            @PathVariable String userId,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(patientService.getPatientByUserId(userId, getUser(jwt)));
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientResponse> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(patientService.updatePatient(id, request, getUser(jwt)));
    }

    // ========== PRACTITIONER ENDPOINTS ==========
    @PostMapping("/practitioners")
    public ResponseEntity<PractitionerResponse> createPractitioner(
            @Valid @RequestBody PractitionerRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(practitionerService.createPractitioner(request, getUser(jwt)));
    }

    @GetMapping("/practitioners")
    public ResponseEntity<List<PractitionerResponse>> getAllPractitioners() {
        return ResponseEntity.ok(practitionerService.getAllPractitioners());
    }

    @GetMapping("/practitioners/type/{type}")
    public ResponseEntity<List<PractitionerResponse>> getPractitionersByType(
            @PathVariable PractitionerType type) {
        return ResponseEntity.ok(practitionerService.getPractitionersByType(type));
    }

    @GetMapping("/practitioners/{id}")
    public ResponseEntity<PractitionerResponse> getPractitionerById(@PathVariable Long id) {
        return ResponseEntity.ok(practitionerService.getPractitionerById(id));
    }

    @GetMapping("/practitioners/me")
    public ResponseEntity<PractitionerResponse> getCurrentPractitioner(
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(practitionerService.getPractitionerByUserId(getUser(jwt).getId()));
    }

    // ========== CONDITION ENDPOINTS ==========
    @PostMapping("/conditions")
    public ResponseEntity<ConditionResponse> createCondition(
            @Valid @RequestBody ConditionRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(conditionService.createCondition(request, getUser(jwt)));
    }

    @GetMapping("/conditions/patient/{patientId}")
    public ResponseEntity<List<ConditionResponse>> getConditionsByPatientId(
            @PathVariable Long patientId,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(conditionService.getConditionsByPatientId(patientId, getUser(jwt)));
    }

    // ========== ENCOUNTER ENDPOINTS ==========
    @PostMapping("/encounters")
    public ResponseEntity<EncounterResponse> createEncounter(
            @Valid @RequestBody EncounterRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(encounterService.createEncounter(request, getUser(jwt)));
    }

    @GetMapping("/encounters/patient/{patientId}")
    public ResponseEntity<List<EncounterResponse>> getEncountersByPatientId(
            @PathVariable Long patientId,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(encounterService.getEncountersByPatientId(patientId, getUser(jwt)));
    }

    @GetMapping("/encounters/practitioner/{practitionerId}")
    public ResponseEntity<List<EncounterResponse>> getEncountersByPractitionerId(
            @PathVariable Long practitionerId,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(encounterService.getEncountersByPractitionerId(practitionerId, getUser(jwt)));
    }

    @GetMapping("/encounters/{id}")
    public ResponseEntity<EncounterResponse> getEncounterById(
            @PathVariable Long id,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(encounterService.getEncounterById(id, getUser(jwt)));
    }

    // ========== OBSERVATION ENDPOINTS ==========
    @PostMapping("/observations")
    public ResponseEntity<ObservationResponse> createObservation(
            @Valid @RequestBody ObservationRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(observationService.createObservation(request, getUser(jwt)));
    }

    @GetMapping("/observations/patient/{patientId}")
    public ResponseEntity<List<ObservationResponse>> getObservationsByPatientId(
            @PathVariable Long patientId,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(observationService.getObservationsByPatientId(patientId, getUser(jwt)));
    }

    // ========== LOCATION ENDPOINTS ==========
    @PostMapping("/locations")
    public ResponseEntity<LocationResponse> createLocation(
            @Valid @RequestBody LocationRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(locationService.createLocation(request, getUser(jwt)));
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    // ========== ORGANIZATION ENDPOINTS ==========
    @PostMapping("/organizations")
    public ResponseEntity<OrganizationResponse> createOrganization(
            @Valid @RequestBody OrganizationRequest request,
            @AuthenticationPrincipal org.springframework.security.oauth2.jwt.Jwt jwt) {
        return ResponseEntity.ok(organizationService.createOrganization(request, getUser(jwt)));
    }

    @GetMapping("/organizations")
    public ResponseEntity<List<OrganizationResponse>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @GetMapping("/organizations/{id}")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.getOrganizationById(id));
    }

}
