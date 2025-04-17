package co.uniquindio.dtos.request;

import lombok.*;

public record UserUpdateRequest(
    String fullName,
    String city,
    String phoneNumber,
    String email,
    String address
){}
