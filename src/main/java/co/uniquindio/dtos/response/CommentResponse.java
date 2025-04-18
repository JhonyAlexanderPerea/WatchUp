package co.uniquindio.dtos.response;

import java.time.LocalDateTime;
import lombok.*;

public record CommentResponse(
        String userName,
        String userId,
        String comment,
        LocalDateTime date
) {

}
