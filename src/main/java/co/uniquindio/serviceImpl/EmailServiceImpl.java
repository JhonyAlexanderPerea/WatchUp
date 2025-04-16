package co.uniquindio.serviceImpl;

import co.uniquindio.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String to, String subject, String content) {
        System.out.println("📧 Enviando correo a " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Contenido: " + content);
    }

    @Override
    public void sendActivationEmail(String to, String subject, String content) {
        System.out.println("📧 Enviando correo de activación a " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Contenido: " + content);
    }
}
