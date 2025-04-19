package co.uniquindio.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.enums.ReportStatus;
import lombok.*;

public record ReportResponse(
        String id,
        String title,
        String userId,
        List <CategoryResponse> categories,
        String description,
        Location location,
        ReportStatus status,
        int important,
        int isFake,
        LocalDateTime creationDate,
        List<CommentResponse> comments,
        List<String>images
) {

}
