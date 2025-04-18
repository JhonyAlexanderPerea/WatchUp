package co.uniquindio.mappers;
import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.model.Category;
import co.uniquindio.model.Report;
import co.uniquindio.repository.CategoryRepository;
import co.uniquindio.service.CategoryService;
import org.mapstruct.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", defaultValue = "PENDING")
    @Mapping(target = "creationDate", defaultExpression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "important", defaultValue = "0")
    @Mapping(target = "isFake", defaultValue = "0")
    @Mapping(target = "comments", defaultExpression = "java(new java.util.ArrayList<>()")
    @Mapping(target = "images", expression = "java(convertMultipartFilesToBytes(reportRequest.images()))")
    @Mapping(target = "location", source = "location", qualifiedByName = "java(locationToGeoJsonPoint)")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "java(mapCategoryNamesToCategories)")
    Report parseOf(ReportRequest reportRequest);


    @Mapping(target = "location", source = "location", qualifiedByName = "java(geoJsonPointToLocation)")
    @Mapping(target = "images", expression = "java(convertBytesToBase64(report.getImages()))")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "java(mapCategoriesToStrings)")
    ReportResponse toResponse(Report report);

    // Métodos de conversión personalizados
    default List<String> convertBytesToBase64(List<byte[]> imagenes) {
        return imagenes.stream()
                .map(bytes -> "data:image/png;base64," + Base64.getEncoder().encodeToString(bytes))
                .toList();
    }

    default List<byte[]> convertMultipartFilesToBytes(List<MultipartFile> imagenes) {
        return imagenes.stream()
                .map(file -> {
                    try {
                        return file.getBytes();
                    } catch (IOException e) {
                        throw new RuntimeException("Error al convertir MultipartFile a byte[]", e);
                    }
                })
                .toList();
    }

    @Named("locationToGeoJsonPoint")
    default GeoJsonPoint locationToGeoJsonPoint(Location location) {
        if (location == null) {
            return null;
        }
        // GeoJsonPoint espera [longitud, latitud]
        return new GeoJsonPoint(
                location.coordinates()[0], // Longitud
                location.coordinates()[1]  // Latitud
        );
    }
    @Named("geoJsonPointToLocation")
    default Location geoJsonPointToLocation(GeoJsonPoint geoJsonPoint) {
        if (geoJsonPoint == null) {
            return null;
        }
        // Convertimos a [longitud, latitud]
        return new Location(
                "Point",
                new Double[] {
                        geoJsonPoint.getX(), // Longitud
                        geoJsonPoint.getY() // Latitud
                }
        );
    }

    @Named("mapCategoriesToStrings")
    default List<String> mapCategoriesToStrings(List<Category> categories) {
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories.stream()
                .map(Category::getName) // Extrae el nombre de cada categoría
                .filter(Objects::nonNull) // Filtra nombres nulos
                .toList();
    }

    @Named("mapCategoryNamesToCategories")
    default List<Category> mapCategoryNamesToCategories(List<String> categoryNames, @Context CategoryService categoryService) {
        if (categoryNames == null) {
            return new ArrayList<>();
        }
        return categoryNames.stream()
                .map(categoryName ->
                        Optional.of(categoryService.getCategoryByName(categoryName))
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryName))
                )
                .toList();
    }
    
    
}
