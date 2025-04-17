package co.uniquindio.dtos.common;

import lombok.Builder;

@Builder
public record PaginatedContent(
    int totalPages,
    int totalElements,
    int currentPage,
    int pageSize
) {}
