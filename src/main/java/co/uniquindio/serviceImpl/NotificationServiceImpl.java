package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.response.PaginatedNotificationResponse;
import co.uniquindio.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public PaginatedNotificationResponse getUserNotifications(int page, int size) {
        //TODO
        return null;
    }

    @Override
    public void markNotificationRead(String notificationId) {
        //TODO
    }

    @Override
    public void sendRealTimeNotification(String message) {
        //TODO
    }
}
