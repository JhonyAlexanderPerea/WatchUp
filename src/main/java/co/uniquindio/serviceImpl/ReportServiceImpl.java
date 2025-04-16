package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.request.ReportChangeStatusRequest;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.request.ReportUpdateRequest;
import co.uniquindio.dtos.response.*;
import co.uniquindio.dtos.response.auth.ReportResponse;
import co.uniquindio.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public ReportResponse createReport(ReportRequest request) {
        //TODO
        return null;
    }

    @Override
    public PaginatedReportResponse getReports(int page, int size, Optional<String> status, Optional<String> category, Optional<Boolean> isImportant, Optional<Double> radius, Optional<Double> lat, Optional<Double> lng, PageInfoResponse pageInfo) {
        //TODO
        return null;
    }

    @Override
    public ReportDetailResponse getReport(String reportId) {
        //TODO
        return null;
    }

    @Override
    public ReportResponse changeReportStatus(String reportId, ReportChangeStatusRequest request) {
        //TODO
        return null;
    }

    @Override
    public ReportResponse updateReport(String id, ReportUpdateRequest request) {
        //TODO
        return null;
    }

    @Override
    public void deleteReport(String id) {
        //TODO
    }

    @Override
    public void markReportAsImportant(String id) {
        //TODO
    }

    @Override
    public void unmarkReportAsImportant(String id) {
        //TODO
    }

    @Override
    public PaginatedReportResponse getUserReports(String userId, int page, int size) {
        //TODO
        return null;
    }

    @Override
    public PaginatedCommentResponse getCommentsReport(String reportId, int page, int size) {
        //TODO
        return null;
    }

    @Override
    public CommentResponse addCommentReport(String reportId, CommentRequest request) {
        //TODO
        return null;
    }

    @Override
    public StatusHistoryItemResponse getHistoryStatusReport(String reportId) {
        //TODO
        return null;
    }

    @Override
    public byte[] generateReport(LocalDate startDate, LocalDate endDate, List<String> categories, String format) {
        //TODO
        return new byte[0];
    }
}
