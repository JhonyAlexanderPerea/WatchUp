package co.uniquindio.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

import co.uniquindio.dtos.common.Location;
import lombok.*;

public record ReportResponse(
        String id,
        String title,
        List <CategoryResponse> categories,
        String description,
        Location Location,
        ReportResponse status,
        int important,
        int isFake,
        LocalDateTime creationDate,
        List<CommentResponse> comments,
        List<String>images
) {

}
