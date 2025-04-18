package co.uniquindio.service;


import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.mappers.ReportMapper;
import co.uniquindio.model.Report;
import co.uniquindio.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public ReportResponse createReport(ReportRequest reportRequest) {
        Report newReport = reportMapper.parseOf(reportRequest);
        return  reportMapper.toResponse(reportRepository.save(newReport));
    }

    @Override
    public Optional<PaginatedReportResponse> getReports(String title, String userId, String category, String status, String order, LocalDateTime creationDate, int page) {
        return Optional.empty();
    }


    @Override
    public Optional<ReportResponse> getReport(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<ReportResponse> changeReportStatus(String id, ReportStatus status) {
        return Optional.empty();
    }


    @Override
    public Optional<ReportResponse> updateReport(String id, ReportRequest reportRequest) {
        return Optional.empty();
    }

    @Override
    public void deleteReport(String id) {

    }
}