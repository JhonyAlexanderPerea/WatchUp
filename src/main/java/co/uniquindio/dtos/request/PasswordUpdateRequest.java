package co.uniquindio.dtos.request;

import lombok.*;

public record PasswordUpdateRequest(
        String currentPassword,
        String newPassword
) {

}
