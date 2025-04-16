package co.uniquindio.dtos.response;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CommentResponse {
    private String userName;
    private String userId;
    private String comment;
    private LocalDateTime date;
}
