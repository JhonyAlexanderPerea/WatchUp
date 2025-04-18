package co.uniquindio.dtos.response;

public record AuthenticationResponse(
    String token,
    String tokenType,
    UserResponse user
) {
    public AuthenticationResponse {
        // Valor por defecto para tokenType si es null
        if (tokenType == null) {
            tokenType = "Bearer";
        }
    }
}