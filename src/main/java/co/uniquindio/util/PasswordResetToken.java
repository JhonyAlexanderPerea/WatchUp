package co.uniquindio.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document (collection = "password_reset_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

    @Id
    private String id;

    private String code;
    private String userEmail;
    private LocalDateTime expiryDate;

    public PasswordResetToken(String code, String userEmail, LocalDateTime expiryDate) {
        this.code = code;
        this.userEmail = userEmail;
        this.expiryDate = expiryDate;
    }
}
