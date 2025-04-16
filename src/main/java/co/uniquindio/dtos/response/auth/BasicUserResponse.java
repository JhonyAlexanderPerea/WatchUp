package co.uniquindio.dtos.response.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicUserResponse {
    private String id;
    private String email;
    private String fullName;
}
