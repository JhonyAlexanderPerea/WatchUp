package co.uniquindio.repository;

import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.model.Report;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {

    // Búsqueda por categoría y estado
    List<Report> findByUserIdAndStatus(String userId, ReportStatus status);

    // Operadores de comparación
    // Reportes importantes (con más de X "me gusta")
    List<Report> findByImportantGreaterThanOrderByCreationDateDesc(int votes);
    // Operadores de comparación
    List<Report> findByImportantBetween(int min, int max);
    // Reportes de un usuario específico
    List<Report> findByUserIdOrderByCreationDateDesc(String userId);
    // Conteo de reportes entre dos fechas
    long countByCreationDateBetween(LocalDateTime min, LocalDateTime max);

//    @Aggregation({
//            "{ $match: { idUser: ?0 } }",
//            "{ $lookup: { from: 'users', localField: 'idUser', foreignField: '_id', as: 'user' } }
//            "{ $unwind: '$user' }",
//            "{ $project: { title:1, description:1, date: 1, status: 1, fullName: '$user.fullName',
//            "{ $sort: { ratingsImportant: -1 } }"
//    })
//    List<ReportDto> listarReportesConUsuario(String userId);
//
//    @Aggregation({
//            "{ $match: { status: ?0 } }",
//            "{ $group: { _id: '$category', total: { $sum: 1 } } }"
//    })
//    List<CategoryCount> contarPorCategoria(String status);
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
