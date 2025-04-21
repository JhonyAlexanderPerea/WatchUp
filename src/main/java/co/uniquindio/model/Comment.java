package co.uniquindio.model;

import co.uniquindio.enums.CommentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("comment")
public class Comment {
    @EqualsAndHashCode.Include
    @Id
    private String id;
    @Indexed
    private String userId;
    private CommentStatus status;
    @Indexed
    private String reportId;
    private LocalDateTime creationDate;
    private String comment;
}
