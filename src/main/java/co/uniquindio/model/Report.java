package co.uniquindio.model;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.enums.ReportStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@Document("report")
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Report {
    @Id
    @EqualsAndHashCode.Include
    String id;

    String title;
    String description;
    String category;
    ReportStatus status;
    LocalDateTime creationDate;
    List<Image> image;
    int important;
    int isFake;
    List<Comment> comments;
    Location location;

}
