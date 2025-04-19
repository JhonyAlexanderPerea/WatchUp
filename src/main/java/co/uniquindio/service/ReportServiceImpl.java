package co.uniquindio.service;


import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.common.ReportChangeStatus;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.mappers.ReportMapper;
import co.uniquindio.model.Report;
import co.uniquindio.model.User;
import co.uniquindio.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService{
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final CategoryService categoryService;
    private final org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    @Override
    public ReportResponse createReport(ReportRequest reportRequest, List<MultipartFile>images, String userId) {
        Report newReport = reportMapper.parseOf(reportRequest, images, categoryService);
        ObjectId id = new ObjectId(userId);
        if(!userIdIsValid(userId)){
            throw new RuntimeException("El id del usuario no es valido");
        }
        newReport.setUserId(id);
        return  reportMapper.toResponse(reportRepository.save(newReport));
    }

    @Override
    public PaginatedReportResponse getReports(String title, String userId, String category,
                                              String status, String order, LocalDateTime creationDate,
                                              Location location, int page) {
        // 1. Configurar paginación y ordenamiento
        Pageable pageable = PageRequest.of(page, 10, parseSort(order)); // 10 items por página
        org.springframework.data.mongodb.core.query.Query dynamicQuery = new Query().with(pageable);

        // 2. Construir criterios dinámicos
        Criteria criteria = new Criteria();

        if (title != null && !title.isEmpty()) {
            criteria.and("title").regex(title, "i"); // Búsqueda case-insensitive
        }

        if (userId != null && !userId.isEmpty()) {
            criteria.and("userId").is(new ObjectId(userId));
        }

        if (category != null && !category.isEmpty()) {
            criteria.and("categories.$id").is(new ObjectId(category)); // Asume referencia por ID
        }

        if (status != null && !status.isEmpty()) {
            try {
                ReportStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + status);
            }
            criteria.and("status").is(ReportStatus.valueOf(status.toUpperCase()));
        }

        if (creationDate != null) {
            criteria.and("creationDate").gte(creationDate);
        }

        // 3. Filtro geoespacial (si se proporciona ubicación)
        if (location != null && location.coordinates() != null) {
            org.springframework.data.geo.Point point = new Point(location.coordinates().get(0), location.coordinates().get(1));
            criteria.and("location").nearSphere(point).maxDistance(1);
        }

        dynamicQuery.addCriteria(criteria);

        // 4. Ejecutar consulta paginada
        long total = mongoTemplate.count(dynamicQuery, Report.class);
        List<Report> reports = mongoTemplate.find(dynamicQuery, Report.class);

        // 5. Construir respuesta paginada

        return new PaginatedReportResponse(
                reports.stream().map(reportMapper::toResponse).toList(),
                new PaginatedContent((int)((total+9)/pageable.getPageSize()),
                        (int)total,
                        page,
                        10));
    }

    // Metodo para parsear el parámetro de ordenamiento
    private Sort parseSort(String order) {
        if (order == null || order.isEmpty()) return Sort.unsorted();
        String[] parts = order.split(":");
        return Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
    }


    @Override
    public Optional<ReportResponse> getReport(String id) {
        return reportRepository.findById(id).map(reportMapper::toResponse);
    }

    @Override
    public Optional<ReportResponse> changeReportStatus(String id, ReportStatus status) {
        Report report = reportRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontro el reporte con el id: "+id));
        report.setStatus(status);
        reportRepository.save(report);
        return Optional.of(reportMapper.toResponse(report));
    }


    //MODIFICAR PARA QUE GUARDE LAS IMAGENES O QUE SE PUEDAN BORRAR O HACER ESA GESTION DE LAS IMAGENS DEL REPORTE
    @Override
    public Optional<ReportResponse> updateReport(String id, List<MultipartFile>images, ReportRequest reportRequest) {
        if(reportRepository.existsById(id)){
            Report report = reportMapper.parseOf(reportRequest, images, categoryService);
            report.setId(id);
            return Optional.of(reportMapper.toResponse(reportRepository.save(report)));
        }else{
            throw new RuntimeException("No se encontro el reporte con el id: "+id);
        }
    }

    @Override
    public void deleteReport(String id) {
        Report auxReport = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el reporte con el id: " + id));
        auxReport.setStatus(ReportStatus.DELETED);
        reportRepository.save(auxReport);
    }

    public static boolean userIdIsValid(String id) {
        return ObjectId.isValid(id);
    }
}