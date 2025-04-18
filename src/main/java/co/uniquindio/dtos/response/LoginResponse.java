package co.uniquindio.dtos.response;

public record LoginResponse(
    String token,
    String tokenType,
    UserResponse user
) {
    public LoginResponse {
        // Valor por defecto para tokenType si es null
        if (tokenType == null) {
            tokenType = "Bearer";
        }
    }
}