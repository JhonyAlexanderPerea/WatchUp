package co.uniquindio.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public record UserUpdateRequest(
    String fullName,
    String city,
    String phoneNumber,
    String email,
    String address
){}
