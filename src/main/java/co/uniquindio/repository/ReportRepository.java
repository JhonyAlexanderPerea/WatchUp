package co.uniquindio.repository;

import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.model.Category;
import co.uniquindio.model.Report;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {

    List<Report> findReportsByTitleContaining(String title, Sort sort);
    List<Report> findReportsByTitleContainingAndStatus(String title, ReportStatus status, Sort sort);

    List<Report> findReportsByUserId(ObjectId userId, Sort sort);
    List<Report> findReportsByUserIdAndStatus(ObjectId userId, ReportStatus status, Sort sort);
    List<Report> findReportsByUserIdAndTitleContaining(ObjectId userId, String title, Sort sort);
    List<Report> findReportsByUserIdAndTitleContainingAndStatus(ObjectId userId, String title, ReportStatus status, Sort sort);
    List<Report> findReportsByUserIdOrderByCreationDate(ObjectId userId, Sort sort);


    List<Report> findReportsByStatus(ReportStatus status);
    List<Report> findReportByCategories(List<Category> categories);


    List<Report> findReportsByUserIdAndStatus(ObjectId userId, ReportStatus status);
    
    // Operadores de comparación
    // Reportes importantes (con más de X "me gusta")
    List<Report> findByImportantGreaterThanOrderByCreationDateDesc(int votes);
    // Operadores de comparación
    List<Report> findByImportantBetween(int min, int max);


    // Conteo de reportes entre dos fechas
    long countByCreationDateBetween(LocalDateTime min, LocalDateTime max);

@Query("{ " +
        "'location': { " +
        " $near: { " +
        " $geometry: { " +
        " type: 'Point', " +
        " coordinates: [?0, ?1] " + // ?0=longitud, ?1=latitud
        " }, " +
        " $maxDistance: ?2 " + // ?2=distancia en metros
        " } " +
        "}}")
List<Report> findReportsNearLocation(
        double longitude,
        double latitude,
        int distanceInMeters
);
}
