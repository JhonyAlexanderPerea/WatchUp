package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.response.NotificationResponse;
import co.uniquindio.dtos.response.PaginatedNotificationResponse;
import co.uniquindio.enums.NotificationStatus;
import co.uniquindio.exceptions.AccessDeniedException;
import co.uniquindio.exceptions.ApiExceptions;
import co.uniquindio.exceptions.NotFoundException;
import co.uniquindio.mappers.NotificationMapper;
import co.uniquindio.model.Notification;
import co.uniquindio.model.Report;
import co.uniquindio.model.User;
import co.uniquindio.repository.NotificationRepository;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.service.NotificationService;
import co.uniquindio.util.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public CompletableFuture<Void> makeNotifacationToAll(Report report) {
        try{
        List<User> usersNearby = userRepository.findNearUsers(
                report.getLocation().getX(),
                report.getLocation().getY(),
                1000 // Radio en metros
        );
        if (usersNearby != null && !usersNearby.isEmpty()) {
            try {
                usersNearby.remove(userRepository.findById(report.getUserId().toString()));
               usersNearby.stream().anyMatch(user -> user.getId().equals(report.getUserId().toString()) ? usersNearby.remove(user) : true);
            }catch (Exception e){
                System.out.println("ERROR INESPERADO, no se encontro al usuario creador del reporte: "
                        + report.getId() + " en el metodo: makeNotifacationToAll() de la clase: NotificationServiceImpl.java");
            }
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
                    System.out.println("Error al enviar notificacion al usuario: " + user.getFullName());
                    //throw new ApiExceptions.EmailSendException("Error al enviar notificacion al usuario: " + user.getFullName());
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
        return CompletableFuture.completedFuture(null);
    } catch (Exception e) {
        return CompletableFuture.failedFuture(e);
    }
    }

    @Override
    public PaginatedNotificationResponse getNotifications(String userId, String status) {
        return null;
    }

    @Override
    public void deleteNotification(String id) {
        notificationRepository.findById(id)
                .orElseThrow(()->new NotFoundException("No se encontro la notificacion con el id: "+id));
        notificationRepository.deleteById(id);
    }

    @Override
    public NotificationResponse changeNotificationStatus(String id, String userId) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new NotFoundException("No se encontro la notificacion con el id: "+id));
        if(notification.getUserId().equals(userId)) {
            notification.setStatus(NotificationStatus.VIEWED);
            notificationRepository.save(notification);
        }else{
            throw new AccessDeniedException("No eres el usuario de esta notificacion");
        }

        return notificationMapper.toResponse(notification);
    }

    @Override
    public NotificationResponse getNotification(String id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(()->new NotFoundException("No se encontro la notificacion con el id: "+id));
        return notificationMapper.toResponse(notification);
    }
}
