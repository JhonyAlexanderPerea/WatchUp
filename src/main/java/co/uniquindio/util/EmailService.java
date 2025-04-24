package co.uniquindio.util;

import co.uniquindio.exceptions.ApiExceptions;
import jakarta.mail.MessagingException;
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

    public void enviarNotificacionReporte(String destinatario, String tipoReporte,
                                          String idReporte, String fecha,
                                          String descripcion, String enlaceReporte)
            throws MessagingException {

        String colorFondo = "";
        String icono = "";

        switch (tipoReporte.toLowerCase()) {
            case "seguridad":
                colorFondo = "#FFEBEE";
                icono = "🔒";
                break;
            case "transito":
                colorFondo = "#FFF3E0";
                icono = "🚦";
                break;
            case "mascota":
                colorFondo = "#E8F5E9";
                icono = "🐾";
                break;
            default:
                colorFondo = "#E3F2FD";
                icono = "📄";
        }

        String htmlContent = String.format(
                "<html>" +
                        "<head>" +
                        "   <style>" +
                        "       .container { max-width: 600px; margin: 20px auto; padding: 30px; border-radius: 10px; }" +
                        "       .header { font-size: 24px; margin-bottom: 20px; }" +
                        "       .badge { padding: 5px 10px; border-radius: 5px; color: white; }" +
                        "       .details-table { width: 100%%; margin: 20px 0; border-collapse: collapse; }" +
                        "       .details-table td { padding: 10px; border-bottom: 1px solid #ddd; }" +
                        "       .button { display: inline-block; padding: 12px 25px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; }" +
                        "   </style>" +
                        "</head>" +
                        "<body style='font-family: Arial, sans-serif;'>" +
                        "   <div class='container' style='background-color: %s;'>" +
                        "       <div class='header'>" +
                        "           %s Notificación de Reporte #%s" +
                        "       </div>" +
                        "       " +
                        "       <table class='details-table'>" +
                        "           <tr>" +
                        "               <td><strong>Tipo de Reporte:</strong></td>" +
                        "               <td style='background-color: %s;'>%s %s</td>" +
                        "           </tr>" +
                        "           <tr>" +
                        "               <td><strong>Fecha:</strong></td>" +
                        "               <td>%s</td>" +
                        "           </tr>" +
                        "           <tr>" +
                        "               <td><strong>Descripción:</strong></td>" +
                        "               <td>%s</td>" +
                        "           </tr>" +
                        "       </table>" +
                        "       " +
                        "       <p>Para ver más detalles sobre este reporte o realizar seguimiento, haz clic en el siguiente botón:</p>" +
                        "       " +
                        "       <a href='%s' class='button'>Ver Reporte Completo</a>" +
                        "       " +
                        "       <p style='margin-top: 30px; color: #666; font-size: 14px;'>" +
                        "           Este es un mensaje automático. Por favor no responda a este correo." +
                        "       </p>" +
                        "   </div>" +
                        "</body>" +
                        "</html>",
                colorFondo,
                icono, idReporte,
                colorFondo, icono, tipoReporte.toUpperCase(),
                fecha,
                descripcion,
                enlaceReporte);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject("[Notificación] Nuevo reporte de " + tipoReporte);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }


    public void sendEmailNotification(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            message.addHeader("X-Priority", "1");
            message.addHeader("X-MSMail-Priority", "High");
            message.addHeader("Importance", "High");
            helper.setText(content, true);
            mailSender.send(message);
            log.info("Email de restablecimiento de contraseña enviado a: {}", toEmail);
        }catch (MessagingException e) {
            log.error("Error enviando email de notificacion", e);
            throw new ApiExceptions.EmailSendException("Error al enviar el email para notificacion");
        }
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

    public void sendPasswordResetEmail(String toEmail, String resetCode) {
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
                """, resetCode);
            
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