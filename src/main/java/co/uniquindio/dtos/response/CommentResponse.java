package co.uniquindio.dtos.response;

import java.time.LocalDateTime;

import co.uniquindio.enums.CommentStatus;
import lombok.*;

public record CommentResponse(
        String id,
        String userName,
        String userId,
        String reportId,
        String comment,
        CommentStatus status,
        LocalDateTime date
) {

}
