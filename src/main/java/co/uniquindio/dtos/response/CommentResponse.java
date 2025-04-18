package co.uniquindio.dtos.response;

import java.time.LocalDateTime;

import co.uniquindio.enums.CommentStatus;
import lombok.*;

public record CommentResponse(
        String userName,
        String userId,
        String comment,
        CommentStatus status,
        LocalDateTime date
) {

}
