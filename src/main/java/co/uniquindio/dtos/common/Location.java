package co.uniquindio.dtos.common;

import java.util.List;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private String type;             
    private List<Double> coordinates;
}
