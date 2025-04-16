package co.uniquindio.service;

import co.uniquindio.dtos.response.PaginatedNotificationResponse;

public interface NotificationService {
    PaginatedNotificationResponse getUserNotifications(int page, int size);
    void markNotificationRead (String notificationId);
    void sendRealTimeNotification(String message);
}
