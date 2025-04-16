package co.uniquindio.dtos.common;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedContent {
    private int totalPages;
    private int totalElements;
    private int currentPage;
    private int pageSize;
}
