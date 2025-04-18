package co.uniquindio.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank(message = "El email es requerido")
        @Email(message = "El email debe ser válido")
        String email,

        @NotBlank(message = "La contraseña es requerida")
        String password
){}
