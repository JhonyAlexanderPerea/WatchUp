package co.uniquindio.model;

import co.uniquindio.enums.ReportStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Document(collection = "status_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistory {

    @Id
    private String id;

    @DBRef
    private Report report;

    private ReportStatus status;

    @Field("changed_by")
    @DBRef
    private User changedBy;

    @Field("changed_at")
    private LocalDateTime changedAt;

    @Field("rejection_reason")
    private String rejectionReason;
}