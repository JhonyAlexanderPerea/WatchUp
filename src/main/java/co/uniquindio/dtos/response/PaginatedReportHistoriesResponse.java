package co.uniquindio.dtos.response;

import co.uniquindio.dtos.common.PaginatedContent;

import java.util.List;

public record PaginatedReportHistoriesResponse(
        List<ReportHistoryResponse> reportHistories,
        PaginatedContent paginated
) {
}
