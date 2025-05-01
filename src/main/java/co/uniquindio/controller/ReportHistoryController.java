package co.uniquindio.controller;

import co.uniquindio.dtos.response.PaginatedReportHistoriesResponse;
import co.uniquindio.service.ReportHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/reportHistories")
@RestController
public class ReportHistoryController {
    private final ReportHistoryService reportHistoryService;
    @GetMapping
    public PaginatedReportHistoriesResponse getReportHistories(@RequestParam (required = false) String reportId,
                                                                       @RequestParam (required = false) LocalDateTime minorDate,
                                                                       @RequestParam (required = false) LocalDateTime moreDate,
                                                               @RequestParam (required = false) Integer page){
        int pageNumber = (page != null) ? page : 0;
    return reportHistoryService.getReportHistories(reportId,minorDate,moreDate, pageNumber);
    }

}
