package co.uniquindio.util;

import co.uniquindio.dtos.request.*;
import co.uniquindio.dtos.response.LoginResponse;
import co.uniquindio.dtos.response.UserResponse;
import co.uniquindio.enums.UserStatus;
import co.uniquindio.exceptions.ApiExceptions;
import co.uniquindio.model.User;
import co.uniquindio.repository.PasswordResetTokenRepository;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.mappers.UserMapper;
import co.uniquindio.serviceImpl.PasswordResetTokenServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordResetTokenServiceImpl passwordResetTokenService;
    private final PasswordResetTokenRepository resetTokenRepository;

    public LoginResponse login(AuthenticationRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new ApiExceptions.InvalidCredentialsException("Credenciales inválidas"));

        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new ApiExceptions.AccountNotActivatedException("La cuenta no está activada");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ApiExceptions.InvalidCredentialsException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);
        UserResponse userResponse = userMapper.userToUserResponse(user);

        return new LoginResponse(token, "Bearer", userResponse);
    }

    public UserResponse register(RegisterRequest request) {
        log.debug("Iniciando registro de usuario con email: {}", request.email());

        if (userRepository.existsByEmail(request.email())) {
            throw new ApiExceptions.EmailAlreadyExistsException("El email ya está registrado");
        }

        try {
            String activationCode = generateCode();
            LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);

            User user = userMapper.registerRequestToUser(request);
            user.setPassword(passwordEncoder.encode(request.password()));
            user.setStatus(UserStatus.REGISTERED);
            user.setActivationCode(activationCode);
            user.setActivationCodeExpiry(expiryTime);

            User savedUser = userRepository.save(user);
            emailService.sendActivationEmail(savedUser.getEmail(), activationCode);

            return userMapper.userToUserResponse(savedUser);
        } catch (Exception e) {
            log.error("Error al registrar usuario: ", e);
            throw new ApiExceptions.InternalServerErrorException("Error al registrar el usuario: " + e.getMessage());
        }
    }

    public void activateAccount(AccountActivationRequest request) {
        User user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new ApiExceptions.NotFoundException("Usuario no encontrado"));

        if (user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new ApiExceptions.InvalidOperationException("La cuenta ya está activada");
        }

        if (user.getActivationCodeExpiry().isBefore(LocalDateTime.now())) {
            throw new ApiExceptions.InvalidOperationException("El código de activación ha expirado");
        }

        if (!user.getActivationCode().equals(request.activationCode())) {
            throw new ApiExceptions.InvalidOperationException("Código de activación inválido");
        }

        user.setStatus(UserStatus.ACTIVE);
        user.setActivationCode(null);
        user.setActivationCodeExpiry(null);
        userRepository.save(user);
    }

    public void resendActivation(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ApiExceptions.NotFoundException("Usuario no encontrado"));

        if (user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new ApiExceptions.InvalidOperationException("La cuenta ya está activada");
        }

        String newActivationCode = generateCode();
        LocalDateTime newExpiryTime = LocalDateTime.now().plusMinutes(15);

        user.setActivationCode(newActivationCode);
        user.setActivationCodeExpiry(newExpiryTime);
        userRepository.save(user);

        emailService.sendActivationEmail(email, newActivationCode);
    }

    public void sendPasswordResetToken(String email) {
        // Llamamos al servicio para generar y guardar el código UUID
        String resetCode = passwordResetTokenService.generateAndSavePasswordResetToken(email);

        // Enviar el código de restablecimiento por correo
        emailService.sendPasswordResetEmail(email, resetCode);
    }

    public void resetPassword(String code, String newPassword) {
        // Validar el código de restablecimiento y obtener el usuario
        User user = passwordResetTokenService.validatePasswordResetToken(code);

        // Cambiar la contraseña del usuario
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Eliminar el token después de usarlo
        PasswordResetToken token = resetTokenRepository.findByCode(code)
                .orElseThrow(() -> new ApiExceptions.InvalidOperationException("Token no encontrado"));
        passwordResetTokenService.deleteToken(token);
    }
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public boolean isCurrentUser(String id) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findById(id)
                .map(user -> user.getEmail().equals(username))
                .orElse(false);

    }
}