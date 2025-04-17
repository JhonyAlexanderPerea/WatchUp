package co.uniquindio.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterRequest {
    @NotBlank(message = "El nombre completo no puede estar vacío")
    private String fullName;

    private String city;
    private String phoneNumber;
    private String address;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La longitud mínima es 8 caracteres")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "La contraseña debe contener al menos un número, una letra minúscula y una letra mayúscula"
    )
    private String password;

}
