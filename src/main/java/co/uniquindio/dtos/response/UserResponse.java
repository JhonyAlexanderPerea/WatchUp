package co.uniquindio.dtos.response;

import co.uniquindio.enums.Role;
import co.uniquindio.model.Location;

public class UserResponse {
    private String id;
    private String fullName;
    private String city;
    private String email;
    private String phoneNumber;
    private boolean isActive;
    private Role role;
    private Location location;
}
