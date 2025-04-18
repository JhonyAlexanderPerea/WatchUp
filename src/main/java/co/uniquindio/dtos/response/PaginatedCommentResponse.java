package co.uniquindio.dtos.response;

import java.util.List;
import co.uniquindio.dtos.common.PaginatedContent;

public record PaginatedCommentResponse (
    List <CommentResponse> comments,
    PaginatedContent paginated
){}