package co.uniquindio.dtos.response;

import java.time.LocalDateTime;

public record NotificationResponse(
        String id,
        String reportId,
        String userId,
        String status,
        LocalDateTime creationDate
) {
}
