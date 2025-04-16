package co.uniquindio.dtos.response;

import co.uniquindio.enums.Role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String fullName;
    private String city;
    private String phoneNumber;
    private String address;
    private String email;
    private Role role;
    private boolean isActive;
}
