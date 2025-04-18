package co.uniquindio.dtos.response;

import co.uniquindio.dtos.common.PaginatedContent;

import java.util.List;
import lombok.*;

public record PaginatedUserResponse(
        List<UserResponse> users,
        PaginatedContent paginated
) {

}
