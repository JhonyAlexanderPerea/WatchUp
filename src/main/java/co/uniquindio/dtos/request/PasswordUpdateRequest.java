package co.uniquindio.dtos.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordUpdateRequest {
    private String currentPassword;
    private String newPassword;
}
