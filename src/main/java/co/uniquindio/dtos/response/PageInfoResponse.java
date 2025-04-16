package co.uniquindio.dtos.response;

import lombok.Data;

@Data
public class PageInfoResponse {
    private int totalPages;
    private int totalElements;
    private int currentPage;
    private int pageSize;
    private boolean hasNext;
}
