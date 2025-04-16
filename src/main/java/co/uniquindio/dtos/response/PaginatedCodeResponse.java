package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedCodeResponse {
    private List<CodeResponse> codes;
    private PaginatedContent paginated;
}
