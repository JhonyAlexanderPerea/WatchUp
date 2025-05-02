package co.uniquindio.serviceImpl;


import co.uniquindio.mappers.UserMapper;
import co.uniquindio.model.User;

import co.uniquindio.repository.PasswordResetTokenRepository;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.exceptions.ApiExceptions;
import co.uniquindio.service.PasswordResetTokenService;
import co.uniquindio.util.PasswordResetToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository resetTokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    // ✅ Generar un código de restablecimiento y guardarlo en la base de datos
    public String generateAndSavePasswordResetToken(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ApiExceptions.NotFoundException("Usuario no encontrado"));

        String resetCode = UUID.randomUUID().toString();

        PasswordResetToken token = new PasswordResetToken(
                resetCode,
                user.getEmail(),
                LocalDateTime.now().plusMinutes(15)
        );

        resetTokenRepository.save(token);

        return resetCode;
    }

    // ✅ Validar el código de restablecimiento
    public User validatePasswordResetToken(String code) {
        PasswordResetToken token = resetTokenRepository.findByCode(code)
                .orElseThrow(() -> new ApiExceptions.InvalidOperationException("Código de restablecimiento inválido"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ApiExceptions.InvalidOperationException("El código de restablecimiento ha expirado");
        }

        return userRepository.findUserByEmail(token.getUserEmail())
                .orElseThrow(() -> new ApiExceptions.NotFoundException("Usuario no encontrado"));
    }

    // ✅ Eliminar el token una vez usado
    public void deleteToken(PasswordResetToken token) {
        resetTokenRepository.delete(token);
    }
}
