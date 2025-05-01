package co.uniquindio.service;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.response.PaginatedReportHistoriesResponse;
import co.uniquindio.dtos.response.ReportHistoryResponse;
import co.uniquindio.model.ReportHistory;
import co.uniquindio.repository.ReportHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportHistoryServiceImpl implements ReportHistoryService {
    private final ReportHistoryRepository reportHistoryRepository;

    @Override
    public ReportHistory saveReportHistory(String reportId, String userId, String action, String description) {
        return reportHistoryRepository.save(new ReportHistory(
                UUID.randomUUID().toString(),
                reportId,
                description,
                LocalDateTime.now(),
                action,
                userId));
    }

    @Override
    public PaginatedReportHistoriesResponse getReportHistories(String reportId, LocalDateTime minorDate, LocalDateTime moreDate, int page) {
        List<ReportHistoryResponse> reportHistories = new ArrayList<>();
        if(reportId==null || reportId.isEmpty()
            && minorDate==null && moreDate==null){
            reportHistories = reportHistoryRepository.findReportHistoriesdWithTitleAndCreationDate();
        }
        if(reportId!=null && !reportId.isEmpty()){
            reportHistories = reportHistoryRepository.findReportHistoriesByReportIdWithTitleAndCreationDate(reportId);
        }
        if(minorDate==null && moreDate!=null && reportId==null){
            reportHistories = reportHistoryRepository.findReportHistoriesByRegisterDateBetweenWithTitleAndCreationDate(LocalDateTime.MIN,moreDate);
        }
        if(minorDate!=null && moreDate==null && reportId==null){
            reportHistories = reportHistoryRepository.findReportHistoriesByRegisterDateBetweenWithTitleAndCreationDate(minorDate,LocalDateTime.MAX);
        }
        int totalPages = (reportHistories.size() + 9) / 10;
        int totalItems = reportHistories.size();

        reportHistories = reportHistories.subList(page*10,((page+1)*10)-1);

        return new PaginatedReportHistoriesResponse(reportHistories,
                new PaginatedContent(totalPages, totalItems, page, 10));
    }
}
