package co.uniquindio.controller;

import co.uniquindio.dtos.request.AccountActivationRequest;
import co.uniquindio.dtos.request.LoginRequest;
import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.response.TokenResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.util.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/activate")
    public ResponseEntity<Void> activateAccount(
            @Valid @RequestBody AccountActivationRequest request
    ) {
        authService.activateAccount(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resend-activation")
    public ResponseEntity<Void> resendActivation(
            @RequestParam String email
    ) {
        authService.resendActivation(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @RequestParam String email
    ) {
        authService.sendPasswordResetToken(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword
    ) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok().build();
    }
}