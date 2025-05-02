package co.uniquindio.service;

import co.uniquindio.dtos.request.LoginRequest;
import co.uniquindio.dtos.response.TokenResponse;

public interface SecurityService {
    TokenResponse login(LoginRequest request);

}
