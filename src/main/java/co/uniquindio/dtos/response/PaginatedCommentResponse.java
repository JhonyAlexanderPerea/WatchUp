package co.uniquindio.dtos.response;

import java.util.List;
import co.uniquindio.dtos.common.PaginatedContent;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaginatedCommentResponse {
    private List <CommentResponse> comments;
    private PaginatedContent paginated;
}