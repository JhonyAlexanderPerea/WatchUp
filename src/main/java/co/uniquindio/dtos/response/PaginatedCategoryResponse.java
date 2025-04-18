package co.uniquindio.dtos.response;

import java.util.List;
import co.uniquindio.dtos.common.PaginatedContent;

public record PaginatedCategoryResponse (
    List<CategoryResponse> categories,
    PaginatedContent paginated
){}
