package co.uniquindio.dtos.response;

import java.util.List;
import co.uniquindio.dtos.common.PaginatedContent;

public record PaginatedCodeResponse (
    List<CodeResponse> codes,
    PaginatedContent paginated
){}
