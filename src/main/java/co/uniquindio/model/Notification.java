package co.uniquindio.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import co.uniquindio.enums.NotificationType;

@Document(collection = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Report report;

    private NotificationType type;

    private String message;

    @Field("is_read")
    private boolean isRead;

    @Field("created_at")
    private LocalDateTime createdAt;

}