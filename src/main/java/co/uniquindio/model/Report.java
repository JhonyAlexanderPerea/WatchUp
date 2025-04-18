package co.uniquindio.model;

import co.uniquindio.dtos.common.Location;
import co.uniquindio.enums.ReportStatus;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private String id;
    private ObjectId userId;
    private String title;
    private String description;
    @DBRef
    private List<Category> categories;
    private ReportStatus status;
    private LocalDateTime creationDate;
    private List<byte[]> images;
    private int important;
    private int isFake;
    @DBRef
    private List<Comment> comments;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

}
