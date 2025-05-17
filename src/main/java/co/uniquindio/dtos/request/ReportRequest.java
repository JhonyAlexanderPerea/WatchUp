package co.uniquindio.dtos.request;

import java.util.List;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public record ReportRequest (
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        List<String> categories,
        @NotBlank
        Location location
){
}
