package co.uniquindio.dtos.request;

import java.util.List;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.model.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public record ReportRequest (
        String title,
        String description,
        List<String> categories,
        Location location,
        List<MultipartFile> images
){

}
