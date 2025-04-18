package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;

public record PaginatedReportResponse (
        List<ReportResponse> reports,
        PaginatedContent paginated
){

}
