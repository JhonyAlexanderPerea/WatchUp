package co.uniquindio.service;

import co.uniquindio.dtos.request.auth.ActivateRequest;
import co.uniquindio.dtos.request.auth.LoginRequest;
import co.uniquindio.dtos.request.auth.RegisterRequest;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.dtos.response.auth.AuthResponse;
import co.uniquindio.dtos.response.auth.BasicUserResponse;

public interface AuthService {
    BasicUserResponse registerUser (RegisterRequest registerRequest);
    void activateUser (ActivateRequest request);
    UserResponse login (LoginRequest loginRequest);
    void forgotPassword(String email);
    void resetPassword(String email, String resetCode, String newPassword);
}
