package co.uniquindio.dtos.response;

import co.uniquindio.enums.Role;

public record UserResponse (
    String id,
    String fullName,
    String city,
    String email,
    String phoneNumber,
    String address,
    boolean isActive,
    Role role
){}
