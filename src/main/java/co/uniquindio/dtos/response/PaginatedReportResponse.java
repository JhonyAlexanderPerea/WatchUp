package co.uniquindio.dtos.response;

import co.uniquindio.dtos.response.auth.ReportResponse;

import java.util.List;

public class PaginatedReportResponse {
    private List<ReportResponse> content;
    private PageInfoResponse pageInfo;
}
