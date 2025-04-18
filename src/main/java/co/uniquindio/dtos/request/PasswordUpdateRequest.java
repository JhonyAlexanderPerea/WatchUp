package co.uniquindio.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PasswordUpdateRequest (
        @NotBlank(message = "La contraseña actual no puede estar vacía")
        String currentPassword,

        @NotBlank(message = "La nueva contraseña no puede estar vacía")
        @Size(min = 8, message = "La longitud mínima es 8 caracteres")
        @Pattern(
                regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
                message = "La contraseña debe contener al menos un número, una letra minúscula y una letra mayúscula")
        String newPassword

){}
