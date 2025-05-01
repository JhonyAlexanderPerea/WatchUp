package co.uniquindio.service;

import co.uniquindio.dtos.response.PaginatedReportHistoriesResponse;
import co.uniquindio.model.ReportHistory;

import java.time.LocalDateTime;

public interface ReportHistoryService {
    ReportHistory saveReportHistory(String reportId, String userId, String action, String description);
    PaginatedReportHistoriesResponse getReportHistories(String reportId, LocalDateTime minorDate , LocalDateTime moreDate, int page);
}
