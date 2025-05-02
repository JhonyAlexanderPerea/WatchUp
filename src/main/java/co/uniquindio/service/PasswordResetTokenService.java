package co.uniquindio.service;

import co.uniquindio.model.User;
import co.uniquindio.util.PasswordResetToken;

public interface PasswordResetTokenService {
    String generateAndSavePasswordResetToken(String email);
    User validatePasswordResetToken(String code);
    void deleteToken(PasswordResetToken token);
}
