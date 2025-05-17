package co.uniquindio.service;

import co.uniquindio.dtos.response.PaginatedReportHistoriesResponse;
import co.uniquindio.model.ReportHistory;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;

public interface ReportHistoryService {
    ReportHistory saveReportHistory(String reportId, String userId, String action, String description);
    @PreAuthorize("hasAuthority('ADMIN')")
    PaginatedReportHistoriesResponse getReportHistories(String reportId, LocalDateTime minorDate , LocalDateTime moreDate, int page);
}
