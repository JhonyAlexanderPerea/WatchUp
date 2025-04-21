package co.uniquindio.mappers;
import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.model.Category;
import co.uniquindio.model.Report;
import co.uniquindio.repository.CategoryRepository;
import co.uniquindio.service.CategoryService;
import org.bson.types.ObjectId;
import org.mapstruct.*;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {CommentMapper.class, CategoryMapper.class, CategoryService.class})
public interface ReportMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "important", constant = "0")
    @Mapping(target = "isFake", constant = "0")
    @Mapping(target = "comments", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "images", expression = "java(convertMultipartFilesToBytes(images))")
    @Mapping(target = "location", expression = "java(locationToGeoJsonPoint(reportRequest.location()))")
    @Mapping(target = "categories", expression = "java(mapCategoryNamesToCategories(reportRequest.categories(), categoryService))")
    @Mapping(target="usersGaveImportant", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target="usersGaveIsFake", expression = "java(new java.util.ArrayList<>())")
    Report parseOf(ReportRequest reportRequest, List<MultipartFile>images, @Context CategoryService categoryService);


    @Mapping(target = "location", expression = "java(geoJsonPointToLocation(report.getLocation()))")
    @Mapping(target = "images", expression = "java(convertBytesToBase64(report.getImages()))")
    @Mapping(target = "categories", source = "categories")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "userId", expression = "java(ObjectIdToString(report.getUserId()))")
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

    default GeoJsonPoint locationToGeoJsonPoint(Location location) {
        if (location == null) {
            return null;
        }
        // GeoJsonPoint espera [longitud, latitud]
        return new GeoJsonPoint(
                location.coordinates().get(0), // Longitud
                location.coordinates().get(1)  // Latitud
        );
    }

    default Location geoJsonPointToLocation(GeoJsonPoint geoJsonPoint) {
        if (geoJsonPoint == null) {
            return null;
        }
        // Convertimos a [longitud, latitud]
        return new Location(
                "Point",
                new ArrayList<>(Arrays.asList(geoJsonPoint.getX(), geoJsonPoint.getY())) {
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
    default List<Category> mapCategoryNamesToCategories(List<String> categories, @Context CategoryService categoryService) {
        if (categories == null) {
            return new ArrayList<>();
        }
        return categories.stream()
                .map(categoryName ->
                        Optional.of(categoryService.getCategoryByName(categoryName))
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + categoryName))
                )
                .toList();
    }

    default ObjectId toObjectId(String id) {
        return new ObjectId(id); // Convierte String a ObjectId
    }

    default String ObjectIdToString(ObjectId id) {
        return id.toString();
    }
}
