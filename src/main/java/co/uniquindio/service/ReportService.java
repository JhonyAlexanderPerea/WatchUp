package co.uniquindio.service;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.request.ReportChangeStatusRequest;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.request.ReportUpdateRequest;
import co.uniquindio.dtos.response.*;
import co.uniquindio.dtos.response.auth.ReportResponse;

import java.util.Optional;

public interface ReportService {
    ReportResponse createReport(ReportRequest request);
    PaginatedReportResponse getReports (
            Optional<String> status,
            Optional<String> categoryId,
            Optional<Boolean> important,
            Optional<Double> radius,
            Optional<Double> lat,
            Optional<Double> lng,
            PageInfoResponse pageInfo
    );
    ReportDetailResponse getReport(String id);
    ReportResponse chageReportStatus(String id, ReportChangeStatusRequest request);
    ReportResponse updateReport(String id, ReportUpdateRequest request);
    void deleteReport(String id);
    Boolean markReportAsImportant(String id);
    Boolean unmarkReportAsImportant (String id);
    PaginatedReportResponse getUserReports(String userId);
    PaginatedCommentResponse getCommentsReport (String reportId);
    CommentResponse addCommentReport (CommentRequest request);
    StatusHistoryItemResponse getHistoryStatusReport (String reportId);
}