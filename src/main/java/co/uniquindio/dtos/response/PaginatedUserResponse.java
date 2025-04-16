package co.uniquindio.dtos.response;

import co.uniquindio.dtos.common.PaginatedContent;

import java.util.List;

public class PaginatedUserResponse {
    private List<UserResponse> users;
    private PaginatedContent paginated;
}
