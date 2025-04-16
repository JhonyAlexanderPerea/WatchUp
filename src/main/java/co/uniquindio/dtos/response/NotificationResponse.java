package co.uniquindio.dtos.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NotificationResponse {
    private ReportResponse report;
    private boolean isRead;
}
