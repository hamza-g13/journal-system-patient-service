package se.kth.journalsystem.patient.dto.user;

import se.kth.journalsystem.patient.model.enums.UserRole;

public record UserResponse(
                String id,
                String username,
                UserRole role) {

        // Compatibility methods for UserDTO replacement
        public String getId() {
                return id;
        }

        public String getUsername() {
                return username;
        }

        public UserRole getRole() {
                return role;
        }
}
