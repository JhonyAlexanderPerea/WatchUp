package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;

public record PaginatedCodeResponse(
        List<CodeResponse> codes,
        PaginatedContent paginated
) {

}
