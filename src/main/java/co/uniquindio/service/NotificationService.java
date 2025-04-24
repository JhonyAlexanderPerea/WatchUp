package co.uniquindio.service;

import co.uniquindio.dtos.response.NotificationResponse;
import co.uniquindio.dtos.response.PaginatedNotificationResponse;
import co.uniquindio.model.Report;

public interface NotificationService {
    void makeNotifacationToAll(Report report);
    PaginatedNotificationResponse getNotifications(String userId, String status);
    void deleteNotification(String id);
    NotificationResponse changeNotificationStatus(String id);
    NotificationResponse getNotification(String id);
}
