package co.uniquindio.dtos.response;

import java.util.List;

import co.uniquindio.dtos.common.Location;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportResponse {
    private String id;
    private String title;
    private List <CategoryResponse> categories;
    private String description;
    private Location Location;
}
