package co.uniquindio.service;

import co.uniquindio.dtos.request.auth.LoginRequest;
import co.uniquindio.dtos.request.auth.RegisterRequest;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.dtos.response.auth.AuthResponse;
import co.uniquindio.dtos.response.auth.BasicUserResponse;

public interface AuthService {
    BasicUserResponse registerUser (RegisterRequest registerRequest);
    void activateUser (String email, String code);
    UserResponse login (LoginRequest loginRequest);
    Boolean forgotPassword(String email);
    Boolean resetPassword(String email, String resetCode, String newPassword);
}
