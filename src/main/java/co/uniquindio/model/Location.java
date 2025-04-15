package co.uniquindio.model;

import lombok.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String type = "Point";
    private double[] coordinates; // [longitud, latitud]

    public Location(double longitude, double latitude) {
        this.coordinates = new double[]{longitude, latitude};
    }

    public GeoJsonPoint toGeoJsonPoint() {
        return new GeoJsonPoint(coordinates[0], coordinates[1]);
    }
}