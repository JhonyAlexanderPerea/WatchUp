package co.uniquindio.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("reportHistories")
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ReportHistory {
    @EqualsAndHashCode.Include
    @Id
    private String id;
    @Indexed
    private String reportId;
    private String description;
    private LocalDateTime modifiedDate;
    private String action;
    private String modifiedBy;
}
