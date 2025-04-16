package co.uniquindio.dtos.response;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private String name;
    private String description;
    private String id;
}