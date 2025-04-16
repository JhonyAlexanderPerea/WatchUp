package co.uniquindio.service;

import org.springframework.stereotype.Service;


public interface EmailService {
    void sendEmail(String to, String subject, String content);
    void sendActivationEmail(String to, String subject, String content);
}
