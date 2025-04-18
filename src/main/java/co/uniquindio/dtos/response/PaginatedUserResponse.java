package co.uniquindio.dtos.response;

import co.uniquindio.dtos.common.PaginatedContent;
import lombok.Builder;

import java.util.List;

@Builder
public record PaginatedUserResponse (
    List<UserResponse> users,
    PaginatedContent paginated
){}
