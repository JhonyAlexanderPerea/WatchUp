package co.uniquindio.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AccountActivationRequest(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    String email,

    @NotBlank(message = "El código de activación es obligatorio")
    String activationCode
) {}