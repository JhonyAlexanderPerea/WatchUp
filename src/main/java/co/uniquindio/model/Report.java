package co.uniquindio.model;

import co.uniquindio.enums.ReportStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    private String id;

    private String title;

    private String description;

    @Field("status")
    private ReportStatus status;

    @Field("is_important")
    private boolean isImportant;

    @Field("important_count")
    private int importantCount;

    @Field("created_at")
    private LocalDateTime createdAt;

    @DBRef
    @Field("created_by")
    private User createdBy;

    @Field("location")
    private Location location;

    @Field("images")
    private List<String> imageUrls;

    @DBRef
    private List<Category> categories;

    @Field("status_history")
    private List<StatusHistory> statusHistory;

}