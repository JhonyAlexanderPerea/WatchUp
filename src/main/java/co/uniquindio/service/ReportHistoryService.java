package co.uniquindio.service;

import java.time.LocalDateTime;

public interface ReportHistoryService {
    void saveReportHistory(String reportId, String userId);
    PaginatedReportHistories getReportHistories(String reportId, LocalDateTime minorDate , LocalDateTime moreDate);
}
