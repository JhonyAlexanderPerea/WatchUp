package co.uniquindio.controller;


import co.uniquindio.dtos.common.CustomUserDetails;
import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.model.User;
import co.uniquindio.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/reports")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportResponse> createReport(@RequestPart("metadata") ReportRequest reportRequest,
                                                       @RequestPart("images") List<MultipartFile> images,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        String userId = user.getId();
        var reportResponse = reportService.createReport(reportRequest, images,userId);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(reportResponse.id()).
                toUri();
        return ResponseEntity.created(location).body(reportResponse);
    }

    @GetMapping
    PaginatedReportResponse getReports(@RequestParam(required = false)String title,
                                       @RequestParam(required = false)String userId,
                                       @RequestParam(required = false)String category,
                                       @RequestParam(required = false)String status,
                                       @RequestParam(required = false)String order,
                                       @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime registerDate,
                                       @RequestParam(required = false) Location location,
                                       @RequestParam(required = false) @DefaultValue(value = "0") Integer page ){
        int pageNumber = (page != null) ? page : 0;
        return reportService.getReports(title,userId,category,status,order,registerDate,location, pageNumber);
    }

    @GetMapping("/{id}")
    Optional<ReportResponse> getReport(@PathVariable String id){
        return reportService.getReport(id);
    }

    @PatchMapping("/{id}")
    Optional<ReportResponse> changeReportStatus(@PathVariable String id, @RequestBody ReportChangeStatus status){
        return reportService.changeReportStatus(id, status.newStatus());
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Optional<ReportResponse> updateReport(@PathVariable String id,
                                          @RequestPart("metadata") ReportRequest reportRequest,
                                          @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages,
                                          @RequestParam(value = "imagesToDelete" ,required = false)List<Integer> imagesToDelete,
                                          @RequestParam(value = "categoriesToDelete", required = false) List<Integer> categoriesToDelete,
                                          @AuthenticationPrincipal UserDetails userDetails){
        User user = ((CustomUserDetails) userDetails).getUser();
        String userId = user.getId();
        return reportService.updateReport(id, newImages,imagesToDelete,categoriesToDelete, reportRequest, userId);
    }

    @DeleteMapping("/{id}")
    void deleteReport(@PathVariable String id){
        reportService.deleteReport(id);
    }

    @PatchMapping("/{id}/important")
    void increaseImportant(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails){
        User user = ((CustomUserDetails) userDetails).getUser();
        String userId = user.getId();
        reportService.increaseImport(id,userId);
    }
    @PatchMapping("/{id}/fake")
    void increaseIsFake(@PathVariable String id, @AuthenticationPrincipal UserDetails userDetails){
        User user = ((CustomUserDetails) userDetails).getUser();
        String userId = user.getId();
        reportService.increaseIsFake(id,userId);
    }

}
