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
    public PaginatedReportResponse getReports(String title, String userId, String category, String status, String order, LocalDateTime creationDate, int page) {
        return null;
    }


    @Override
    public Optional<ReportResponse> getReport(String id) {
        return reportRepository.findById(id).map(reportMapper::toResponse);
    }

    @Override
    public Optional<ReportResponse> changeReportStatus(String id, ReportStatus status) {
        Report report = reportRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el reporte con el id: "+id));
        report.setStatus(status);
        reportRepository.save(report);
        return Optional.of(reportMapper.toResponse(report));
    }


    @Override
    public Optional<ReportResponse> updateReport(String id, ReportRequest reportRequest) {
        if(reportRepository.existsById(id)){
            Report report = reportMapper.parseOf(reportRequest);
            report.setId(id);
            return Optional.of(reportMapper.toResponse(reportRepository.save(report)));
        }else{
            throw new RuntimeException("No se encontro el reporte con el id: "+id);
        }
    }

    @Override
    public void deleteReport(String id) {
       if(reportRepository.existsById(id)){
           reportRepository.deleteById(id);
       }else{
           throw new RuntimeException("No se encontro el reporte con el id: "+id);
       }
    }
}