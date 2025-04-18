package co.uniquindio.dtos.response;

import co.uniquindio.enums.Role;
import co.uniquindio.enums.UserStatus;

public record UserResponse (
    String id,
    String fullName,
    String city,
    String email,
    String phoneNumber,
    String address,
    UserStatus status,
    Role role
){}
