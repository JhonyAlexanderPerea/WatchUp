package co.uniquindio.dtos.request;

import java.lang.reflect.AccessFlag.Location;
import java.util.List;
import co.uniquindio.model.Category;

public record ReportRequest
(
    String title,
    String description,
    Category category,
    Location location,
    List<String> images
){}
