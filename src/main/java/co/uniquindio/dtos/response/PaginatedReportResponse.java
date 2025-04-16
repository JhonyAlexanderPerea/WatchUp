package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaginatedReportResponse {
    private List<ReportResponse> reports;
    private PaginatedContent paginated;
}
