package co.uniquindio.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document("category")
public class Category {
    @EqualsAndHashCode.Include
    @Id
    private String id;
    private String name;
    private String description;
    private LocalDate creationDate;
}
