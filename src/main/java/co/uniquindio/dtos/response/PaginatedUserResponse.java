package co.uniquindio.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedUserResponse {
    private List<UserResponse> content;
    private PageInfoResponse pageInfo;
}
