package co.uniquindio.model;


import co.uniquindio.enums.NotificationStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Document("notification")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notification {
    @Id
    @EqualsAndHashCode.Include
    String id;
    String userId;
    String reportId;
    NotificationStatus status;
    LocalDateTime creationDate;
}
