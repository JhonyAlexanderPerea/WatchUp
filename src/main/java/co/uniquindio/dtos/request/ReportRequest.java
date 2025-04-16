package co.uniquindio.dtos.request;

import java.lang.reflect.AccessFlag.Location;
import java.util.List;

import co.uniquindio.model.Category;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReportRequest {
    private String title;
    private String description;
    private Category category;
    private Location location;
    private List<String> images;    
}
