package co.uniquindio.util;

import co.uniquindio.exceptions.ApiExceptions;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String toEmail, String activationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Activación de cuenta");

            message.addHeader("X-Priority", "1");
            message.addHeader("X-MSMail-Priority", "High");
            message.addHeader("Importance", "High");


            String content = String.format("""
                <html>
                <body style='font-family: Arial, sans-serif;'>
                    <h2>Activación de Cuenta</h2>
                    <p>Tu código de activación es: <strong>%s</strong></p>
                    <p>Este código expirará en 15 minutos.</p>
                    <p>Por favor, utiliza este código para activar tu cuenta.</p>
                    <br>
                    <p>Si no solicitaste esta activación, por favor ignora este correo.</p>
                </body>
                </html>
                """, activationCode);
            
            helper.setText(content, true);
            mailSender.send(message);
            
            log.info("Email de activación enviado a: {}", toEmail);
        } catch (Exception e) {
            log.error("Error enviando email de activación", e);
            throw new ApiExceptions.EmailSendException("Error al enviar el email de activación");
        }
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Restablecimiento de Contraseña");
            
            String content = String.format("""
                <html>
                <body style='font-family: Arial, sans-serif;'>
                    <h2>Restablecimiento de Contraseña</h2>
                    <p>Has solicitado restablecer tu contraseña.</p>
                    <p>Tu código de restablecimiento es: <strong>%s</strong></p>
                    <p>Este código expirará en 15 minutos.</p>
                    <p>Si no solicitaste este restablecimiento, por favor ignora este correo y asegura tu cuenta.</p>
                    <br>
                    <p>Por razones de seguridad, te recomendamos cambiar tu contraseña regularmente.</p>
                </body>
                </html>
                """, resetToken);
            
            helper.setText(content, true);
            mailSender.send(message);
            
            log.info("Email de restablecimiento de contraseña enviado a: {}", toEmail);
        } catch (Exception e) {
            log.error("Error enviando email de restablecimiento de contraseña", e);
            throw new ApiExceptions.EmailSendException("Error al enviar el email de restablecimiento de contraseña");
        }
    }

    public void sendWelcomeEmail(String toEmail, String fullName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("¡Bienvenido/a a nuestra plataforma!");
            
            String content = String.format("""
                <html>
                <body style='font-family: Arial, sans-serif;'>
                    <h2>¡Bienvenido/a %s!</h2>
                    <p>Gracias por unirte a nuestra plataforma.</p>
                    <p>Tu cuenta ha sido activada exitosamente y ahora puedes acceder a todos nuestros servicios.</p>
                    <br>
                    <p>Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarnos.</p>
                    <p>¡Esperamos que disfrutes de tu experiencia!</p>
                </body>
                </html>
                """, fullName);
            
            helper.setText(content, true);
            mailSender.send(message);
            
            log.info("Email de bienvenida enviado a: {}", toEmail);
        } catch (Exception e) {
            log.error("Error enviando email de bienvenida", e);
            throw new ApiExceptions.EmailSendException("Error al enviar el email de bienvenida");
        }
    }
}