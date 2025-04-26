package co.uniquindio.util;

import co.uniquindio.dtos.common.Location;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class LocationService {

    @Value("${geolocation.default.latitude:4.5709}")
    private double defaultLatitude;

    @Value("${geolocation.default.longitude:-75.6692}")
    private double defaultLongitude;

    private final RestTemplate restTemplate;

    public LocationService() {
        this.restTemplate = new RestTemplate();
    }

    @Data
    private static class IpApiResponse {
        private String ip;
        private String latitude;
        private String longitude;
        private String city;
        private String country;
    }

//    public Location getCurrentLocation() {
//        try {
//            // Usar servicio gratuito de geolocalización por IP
//            IpApiResponse response = restTemplate.getForObject(
//                    "https://ipapi.co/json/",
//                    IpApiResponse.class
//            );
//
//            if (response != null) {
//                return Location.builder()
//                        .type("Point")
//                        .coordinates(List.of(
//                                Double.parseDouble(response.getLongitude()),
//                                Double.parseDouble(response.getLatitude())
//                        ))
//                        .build();
//            }
//        } catch (Exception e) {
//            log.error("Error al obtener la ubicación por IP: {}", e.getMessage());
//        }
//
//        return getDefaultLocation();
//    }
public GeoJsonPoint getCurrentLocation() {
    try {
        // Usar servicio gratuito de geolocalización por IP
        IpApiResponse response = restTemplate.getForObject(
                "https://ipapi.co/json/",
                IpApiResponse.class
        );

        if (response != null) {
            return new GeoJsonPoint(Double.parseDouble(response.getLongitude()),
                                    Double.parseDouble(response.getLatitude()));
        }
    } catch (Exception e) {
        log.error("Error al obtener la ubicación por IP: {}", e.getMessage());
    }

    return getDefaultLocation();
}

//    private Location getDefaultLocation() {
//        return Location.builder()
//                .type("Point")
//                .coordinates(List.of(defaultLongitude, defaultLatitude))
//                .build();
//    }
    private GeoJsonPoint getDefaultLocation() {
        return new GeoJsonPoint(defaultLongitude,
                                defaultLatitude);

    }
}