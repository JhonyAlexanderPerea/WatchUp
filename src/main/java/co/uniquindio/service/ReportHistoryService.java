package co.uniquindio.service;

import co.uniquindio.dtos.response.PaginatedReportHistoriesResponse;
import co.uniquindio.model.ReportHistory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

public interface ReportHistoryService {
    @Async("taskExecutor")
    CompletableFuture<ReportHistory> saveReportHistory(String reportId, String userId, String action, String description);
    @PreAuthorize("hasAuthority('ADMIN')")
    PaginatedReportHistoriesResponse getReportHistories(String reportId, LocalDateTime minorDate , LocalDateTime moreDate, int page);
}
