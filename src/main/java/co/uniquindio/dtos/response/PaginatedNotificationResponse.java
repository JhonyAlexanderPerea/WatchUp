package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;

public record PaginatedNotificationResponse(
        List<NotificationResponse> notifications,
        PaginatedContent paginated) {

}
