package co.uniquindio.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El nombre completo es obligatorio")
        String fullName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "La ciudad es obligatoria")
        String city,

        @NotBlank(message = "El número de teléfono es obligatorio")
        @Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Número de teléfono inválido")
        String phoneNumber,

        @NotBlank(message = "La dirección es obligatoria")
        String address
) {}

