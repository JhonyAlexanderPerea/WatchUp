package co.uniquindio.service;


import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{


    @Override
    public ReportResponse createReport(ReportRequest reportRequest) {
        return null;

    }

    @Override
    public Optional<PaginatedReportResponse> getReports(String title, String userId, String category, String status, String order, LocalDate from, LocalDate registerDate, int page) {
        return Optional.empty();
    }

    @Override
    public Optional<ReportResponse> getReport(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<ReportResponse> changeReportStatus(String id, ReportChangeStatus status) {
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