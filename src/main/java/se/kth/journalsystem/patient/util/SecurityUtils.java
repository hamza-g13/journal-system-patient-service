package se.kth.journalsystem.patient.util;

import org.springframework.security.oauth2.jwt.Jwt;
import se.kth.journalsystem.patient.dto.user.UserResponse;
import se.kth.journalsystem.patient.model.enums.UserRole;

import java.util.List;
import java.util.Map;

public class SecurityUtils {

    public static UserResponse getUserFromJwt(Jwt jwt) {
        if (jwt == null) {
            return null;
        }

        String userId = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");

        // Extract roles from realm_access
        UserRole role = UserRole.PATIENT; // Default
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            // Normalize to uppercase for check
            List<String> upperRoles = roles.stream().map(String::toUpperCase).toList();

            if (upperRoles.contains("ADMIN"))
                role = UserRole.ADMIN;
            else if (upperRoles.contains("DOCTOR"))
                role = UserRole.DOCTOR;
            else if (upperRoles.contains("STAFF"))
                role = UserRole.STAFF;
            else if (upperRoles.contains("PATIENT"))
                role = UserRole.PATIENT;
        }

        return new UserResponse(userId, username, role);
    }
}
