package co.uniquindio.controller;

import co.uniquindio.dtos.request.auth.RegisterRequest;
import co.uniquindio.model.User;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.util.ActivationCodeGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final ActivationCodeGenerator activationCodeGenerator;

    public AuthController(UserRepository userRepository, ActivationCodeGenerator activationCodeGenerator) {
        this.userRepository = userRepository;
        this.activationCodeGenerator = activationCodeGenerator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent())
            return ResponseEntity.status(409).body("Email ya registrado");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFullName(request.getName());
    }
}