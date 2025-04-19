package co.uniquindio.service;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface ReportService {
    ReportResponse createReport(ReportRequest reportRequest, List<MultipartFile>images, String userId);
    PaginatedReportResponse getReports(String title, String userId, String category, String status,
                                       String order, LocalDateTime creationDate, Location location, int page);

    Optional<ReportResponse> getReport(String id);
    Optional<ReportResponse> changeReportStatus(String id, ReportStatus status);
    Optional<ReportResponse> updateReport(String id, List<MultipartFile> newImages,
                                          List<Integer>imagesToDelete,ReportRequest reportRequest, String userId);
    void deleteReport(String id);
}
