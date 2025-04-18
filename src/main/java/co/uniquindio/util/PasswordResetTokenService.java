package co.uniquindio.util;

import co.uniquindio.model.User;

public interface PasswordResetTokenService {
    String generateAndSavePasswordResetToken(String email);
    User validatePasswordResetToken(String code);
    void deleteToken(PasswordResetToken token);
}
