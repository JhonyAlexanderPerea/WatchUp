package co.uniquindio.model;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.enums.Role;
import co.uniquindio.enums.UserStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    @Field("fullName")
    private String fullName;
    private String city;
    @Indexed(unique = true)
    @Email(message = "Debe ser un email válido")
    private String email;
    @Field("phoneNumber")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La longitud mínima es 8 caracteres")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "La contraseña debe contener al menos un número, una letra minúscula y una letra mayúscula"
    )
    private String password;
    @Field("activation_code")
    private String activationCode;
    @Field("activation_code_expiry")
    private LocalDateTime activationCodeExpiry;
    @Field("status")
    private UserStatus status;
    @NotNull(message = "Debe existir un rol")
    private Role role;
    @Field("location")
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    private String resetPasswordToken;

}