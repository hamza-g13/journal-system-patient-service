package se.kth.journalsystem.patient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.kth.journalsystem.patient.dto.location.LocationRequest;
import se.kth.journalsystem.patient.dto.location.LocationResponse;
import se.kth.journalsystem.patient.exception.ForbiddenException;
import se.kth.journalsystem.patient.exception.NotFoundException;
import se.kth.journalsystem.patient.model.Location;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.repository.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

        @Autowired
        private LocationRepository locationRepository;

        @Autowired
        private AuthorizationService authorizationService;

        public LocationResponse createLocation(LocationRequest request, UserResponse currentUser) {
                if (!authorizationService.canCreateLocation(currentUser)) {
                        throw new ForbiddenException("Only admins, doctors and staff can create locations");
                }

                Location location = new Location();
                location.setName(request.name());
                location.setAddress(request.address());
                location.setCity(request.city());
                location.setPostalCode(request.postalCode());
                location.setCountry(request.country());

                location = locationRepository.save(location);

                return new LocationResponse(
                                location.getId(),
                                location.getName(),
                                location.getAddress(),
                                location.getCity());
        }

        public List<LocationResponse> getAllLocations() {
                return locationRepository.findAll()
                                .stream()
                                .map(loc -> new LocationResponse(
                                                loc.getId(),
                                                loc.getName(),
                                                loc.getAddress(),
                                                loc.getCity()))
                                .collect(Collectors.toList());
        }

        public Location findById(Long id) {
                return locationRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Location not found"));
        }

        public LocationResponse getLocationById(Long id) {
                Location location = locationRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Location not found"));

                return new LocationResponse(
                                location.getId(),
                                location.getName(),
                                location.getAddress(),
                                location.getCity());
        }
}
