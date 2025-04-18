package co.uniquindio.dtos.response;


public record NotificationResponse(
        ReportResponse report,
        boolean isRead
) {

}
