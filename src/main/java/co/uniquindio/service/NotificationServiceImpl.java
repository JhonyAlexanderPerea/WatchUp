package co.uniquindio.service;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.response.NotificationResponse;
import co.uniquindio.dtos.response.PaginatedNotificationResponse;
import co.uniquindio.enums.NotificationStatus;
import co.uniquindio.mappers.NotificationMapper;
import co.uniquindio.model.Notification;
import co.uniquindio.model.Report;
import co.uniquindio.model.User;
import co.uniquindio.repository.NotificationRepository;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.util.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public void makeNotifacationToAll(Report report) {
        List<User> usersNearby = userRepository.findNearUsers(
                report.getLocation().getX(),
                report.getLocation().getY(),
                1000 // Radio en metros
        );
        if (usersNearby != null && !usersNearby.isEmpty()) {
            // 2. Crear notificaciones para cada usuario
            List<Notification> notifications = new ArrayList<>();
            for (User user : usersNearby) {
                notifications.add(Notification.builder()
                        .userId(user.getId())
                        .reportId(report.getId())
                        .status(NotificationStatus.NOTIFIED)
                        .creationDate(LocalDateTime.now())
                        .build());
                try {
                    emailService.enviarNotificacionReporte(user.getEmail(),
                            report.getCategories().getFirst().getName(),
                            report.getTitle(),
                            LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-d|HH:mm:ss")),
                            report.getDescription(),
                            "http://localhost/reports/" + report.getId()
                    );
                } catch (MessagingException e) {
                    throw new RuntimeException("Error al enviar notificacion al usuario: " + user.getFullName());
                }

            }
            int totalPages = (notifications.size() + 9) / 10;
            int totalItems = notifications.size();
            notificationRepository.saveAll(notifications);

            //ESTO POR SI EN ALGUN MOMENTO SE DESEA VER LAS NOTIFICACIONES HECHAS
            PaginatedNotificationResponse paginatedNotificationResponse = new PaginatedNotificationResponse(notificationMapper.toResponse(notifications),
                    new PaginatedContent(totalPages, totalItems, 1, 10));
        } else {
            System.out.println("NO SE ENCONTRO NINGUN USUARIO NEARBY PARA LA REPORTE: " + report.getId());
        }
    }

    @Override
    public PaginatedNotificationResponse getNotifications(String userId, String status) {
        return null;
    }

    @Override
    public void deleteNotification(String id) {
        notificationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontro la notificacion con el id: "+id));
        notificationRepository.deleteById(id);
    }

    @Override
    public NotificationResponse changeNotificationStatus(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontro la notificacion con el id: "+id));
        notification.setStatus(NotificationStatus.VIEWED);
        notificationRepository.save(notification);
        return notificationMapper.toResponse(notification);
    }

    @Override
    public NotificationResponse getNotification(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontro la notificacion con el id: "+id));
        return notificationMapper.toResponse(notification);
    }
}
