package co.uniquindio.controller;

import co.uniquindio.dtos.request.AccountActivationRequest;
import co.uniquindio.dtos.request.AuthenticationRequest;
import co.uniquindio.dtos.request.RegisterRequest;
import co.uniquindio.dtos.response.AuthenticationResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.util.AuthenticationService;
import co.uniquindio.util.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationService authService;
    private final EmailService emailService;

    @PostMapping("/login")
    public Optional<ResponseEntity<AuthenticationResponse>> login(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return Optional.of(ResponseEntity.ok(authService.login(request)));
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

    @PostMapping("/send-test-email")
    public ResponseEntity<String> testEmail(@RequestParam String email) {
        try {
            emailService.sendActivationEmail(email, "TEST-123456");
            return ResponseEntity.ok("Email enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al enviar email: " + e.getMessage());
        }
    }

}