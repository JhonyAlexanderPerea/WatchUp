package co.uniquindio.dtos.response;

import co.uniquindio.dtos.common.PaginatedContent;

import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaginatedUserResponse {
    private List<UserResponse> users;
    private PaginatedContent paginated;
}
