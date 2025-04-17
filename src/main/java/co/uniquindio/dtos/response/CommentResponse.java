package co.uniquindio.dtos.response;

import java.time.LocalDateTime;

public record CommentResponse (
    String userName,
    String userId,
    String comment,
    LocalDateTime date
){}
