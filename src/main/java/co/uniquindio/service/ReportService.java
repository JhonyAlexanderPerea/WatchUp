package co.uniquindio.service;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.request.ReportChangeStatusRequest;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.request.ReportUpdateRequest;
import co.uniquindio.dtos.response.*;
import co.uniquindio.dtos.response.auth.ReportResponse;
import co.uniquindio.model.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    ReportResponse createReport(ReportRequest request);
    PaginatedReportResponse getReports (
            int page,
            int size,
            Optional<String> status,
            Optional<String> category,
            Optional<Boolean> isImportant,
            Optional<Double> radius,
            Optional<Double> lat,
            Optional<Double> lng,
            PageInfoResponse pageInfo
    );
    ReportDetailResponse getReport(String reportId);
    ReportResponse changeReportStatus(String reportId, ReportChangeStatusRequest request);
    ReportResponse updateReport(String id, ReportUpdateRequest request);
    void deleteReport(String id);
    void markReportAsImportant(String id);
    void unmarkReportAsImportant (String id);
    PaginatedReportResponse getUserReports(String userId, int page, int size);
    PaginatedCommentResponse getCommentsReport (String reportId, int page, int size);
    CommentResponse addCommentReport (String reportId, CommentRequest request);
    StatusHistoryItemResponse getHistoryStatusReport (String reportId);
    byte [] generateReport(LocalDate startDate, LocalDate endDate, List<String> categories, String format);
}