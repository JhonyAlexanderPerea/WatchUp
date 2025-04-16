package co.uniquindio.dtos.response;

import co.uniquindio.enums.NotificationType;
import co.uniquindio.model.Report;

import java.time.LocalDateTime;

public class NotificationResponse {
    private String id;
    private Report report;
    private NotificationType type;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}
