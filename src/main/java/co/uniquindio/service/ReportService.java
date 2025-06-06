package co.uniquindio.service;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.model.Comment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface ReportService {
    @PreAuthorize("hasAuthority('ADMIN') or isAuthenticated()")
    ReportResponse createReport(ReportRequest reportRequest, List<MultipartFile>images, String userId);

    PaginatedReportResponse getReports(String title, String userId, String category, String status,
                                       String order, LocalDateTime creationDate, Location location, int page);

    Optional<ReportResponse> getReport(String id);
    @PreAuthorize("hasAuthority('ADMIN') or @securityService.isCurrentUser(#userId)")
    Optional<ReportResponse> changeReportStatus(String id, ReportStatus status, String userId);
    @PreAuthorize("hasAuthority('ADMIN') or @securityService.isCurrentUser(#userId)")
    Optional<ReportResponse> updateReport(String id, List<MultipartFile> newImages,
                                          List<Integer>imagesToDelete,
                                          List<Integer> categoriesToDelete,
                                          ReportRequest reportRequest, String userId);
    @PreAuthorize("hasAuthority('ADMIN') or @securityService.isCurrentUser(#userId)")
    void deleteReport(String id, String userId);

    void addComment(String reportId, Comment comment);

    @PreAuthorize("hasAuthority('ADMIN') or isAuthenticated()")
    void increaseImport(String reportId, String userId);
    @PreAuthorize("hasAuthority('ADMIN') or isAuthenticated()")
    void increaseIsFake(String reportId, String userId);
}
