package co.uniquindio.controller;


import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/reports")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public Optional<ReportResponse> createReport(@RequestBody ReportRequest reportRequest) {
        var reportResponse = reportService.createReport(reportRequest);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(reportResponse.id()).
                toUri();
        return ResponseEntity.created(location).body(reportResponse);

    }
    @GetMapping
    Optional<PaginatedReportResponse> getReports(@RequestParam(required = false)String title, @RequestParam(required = false)String userId,
                                                 @RequestParam(required = false)String category, @RequestParam(required = false)String status,
                                                 @RequestParam(required = false)String order,
                                                 @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime registerDate,
                                                 @RequestParam(required = false) @DefaultValue(value = "0") int page ){
        return reportService.getReports(title,userId,category,status,order,registerDate);
    }


}
