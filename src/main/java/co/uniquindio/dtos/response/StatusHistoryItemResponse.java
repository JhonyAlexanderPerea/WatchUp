package co.uniquindio.dtos.response;

import co.uniquindio.enums.ReportStatus;

import java.time.LocalDate;

public class StatusHistoryItemResponse {
    private ReportStatus status;
    private BasicUserInfoResponse changedBy;
    private LocalDate changedAt;
    private String rejectionReason;
}
