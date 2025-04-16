package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedCategoryResponse {
    private List<CategoryResponse> categories;
    private PaginatedContent pagianted;
}
