package co.uniquindio.dtos.request.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String city;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
}
