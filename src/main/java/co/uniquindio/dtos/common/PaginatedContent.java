package co.uniquindio.dtos.common;

import lombok.*;

public record PaginatedContent(
        int totalPages,
        int totalElements,
        int currentPage,
        int pageSize
) {

}
