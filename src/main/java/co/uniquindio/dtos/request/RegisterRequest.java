package co.uniquindio.dtos.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterRequest {
    private String fullName;
    private String city;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;
}
