package co.uniquindio.dtos.request;

public record PasswordUpdateRequest (
    String currentPassword,
    String newPassword
){}
