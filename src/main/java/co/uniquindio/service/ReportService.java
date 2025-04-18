package co.uniquindio.service;

import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;

import java.time.LocalDate;
import java.util.Optional;
import java.time.LocalDateTime;

public interface ReportService {
    ReportResponse createReport(ReportRequest reportRequest);
    Optional<PaginatedReportResponse> getReports(String title, String userId, String category, String status,
                                                 String order, LocalDateTime creationDate, int page);

    Optional<ReportResponse> getReport(String id);
    Optional<ReportResponse> changeReportStatus(String id, ReportStatus status);
    Optional<ReportResponse> updateReport(String id, ReportRequest reportRequest);
    void deleteReport(String id);
}
