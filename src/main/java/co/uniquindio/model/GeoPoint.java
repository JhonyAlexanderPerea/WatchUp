package co.uniquindio.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoPoint {
    @Field("type")
    private String type = "Point"; // Requerido por GeoJSON
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @Field("coordinates")
    private double[] coordinates; // [longitud, latitud] (orden requerido por GeoJSON)
    // Validación de rangos
    public void setCoordinates(double longitude, double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitud debe estar entre -90 y 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitud debe estar entre -180 y 180");
        }
        this.coordinates = new double[]{longitude, latitude};
    }
    // Getters para latitud y longitud
    public double getLatitude() {
        return coordinates[1];
    }
    public double getLongitude() {
        return coordinates[0];
    }
}
