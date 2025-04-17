package co.uniquindio.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "El nombre completo no puede estar vacío")
    String fullName,
    String city,
    String phoneNumber,
    String address,
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    String email,
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La longitud mínima es 8 caracteres")
    @Pattern(
        regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
        message = "La contraseña debe contener al menos un número, una letra minúscula y una letra mayúscula")
    String password
){}
