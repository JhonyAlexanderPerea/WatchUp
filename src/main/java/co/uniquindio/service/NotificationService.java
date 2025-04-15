package co.uniquindio.service;

import co.uniquindio.dtos.response.PaginatednotificationResponse;

public interface NotificationService {
    PaginatednotificationResponse getUserNotifications(int page, int size);
    void markNotificationRead (String notificationId);
}
