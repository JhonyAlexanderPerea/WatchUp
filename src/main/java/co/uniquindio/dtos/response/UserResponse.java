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
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private Role role;
}
