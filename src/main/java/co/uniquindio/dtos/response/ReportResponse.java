package co.uniquindio.dtos.response;

import java.util.List;
import co.uniquindio.dtos.common.Location;

public record ReportResponse (
    String id,
    String title,
    List <CategoryResponse> categories,
    String description,
    Location Location
){}
