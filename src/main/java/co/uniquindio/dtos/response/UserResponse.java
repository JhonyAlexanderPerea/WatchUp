package co.uniquindio.dtos.response;

import co.uniquindio.enums.Role;

import lombok.*;

public record UserResponse (
        String id,
        String fullName,
        String city,
        String phoneNumber,
        String address,
        String email,
        Role role,
        boolean isActive
){

}
