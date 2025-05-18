package co.uniquindio.serviceImpl;


import co.uniquindio.dtos.common.Location;
import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.request.ReportRequest;
import co.uniquindio.dtos.response.PaginatedReportResponse;
import co.uniquindio.dtos.response.ReportResponse;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.enums.Role;
import co.uniquindio.exceptions.InvalidValueException;
import co.uniquindio.exceptions.NotFoundException;
import co.uniquindio.exceptions.NotPermissionException;
import co.uniquindio.mappers.ReportMapper;
import co.uniquindio.model.*;
import co.uniquindio.repository.ReportRepository;
import co.uniquindio.repository.UserRepository;
import co.uniquindio.service.CategoryService;
import co.uniquindio.service.NotificationService;
import co.uniquindio.service.ReportHistoryService;
import co.uniquindio.service.ReportService;
import co.uniquindio.util.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final ReportHistoryService reportHistoryService;
    private final CategoryService categoryService;
    private final NotificationService notificationService;
    private final org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ReportResponse createReport(ReportRequest reportRequest, List<MultipartFile>images, String userId) {
        if(reportRequestIsValid(reportRequest) && images!=null && !images.isEmpty()){

            Report newReport = reportMapper.parseOf(reportRequest, images, categoryService, cloudinaryService);
            ObjectId id = new ObjectId(userId);
            if(!userIdIsValid(userId)){
                throw new RuntimeException("El id del usuario no es valido");
            }
            notificationService.makeNotifacationToAll(newReport);
            newReport.setUserId(id);
            reportHistoryService.saveReportHistory(newReport.getId(), userId,"CREATION",
                    "El usuario con el id :"+userId+" creo el reporte con el id : "+newReport.getId()
            +". En la fecha : "+newReport.getCreationDate().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-d|HH:mm:ss")));
            return  reportMapper.toResponse(reportRepository.save(newReport));
        }else{
            throw new InvalidValueException("Algun campo del reporte no es valido, revise los campos y vuelva a intentarlo");
        }

    }

    @Override
    public PaginatedReportResponse getReports(String title, String userId, String category,
                                              String status, String order, LocalDateTime creationDate,
                                              Location location, int page) {
        // 0. Verificacion rol de usuario
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        // 1. Configurar paginación y ordenamiento
        Pageable pageable = PageRequest.of(page, 10, parseSort(order)); // 10 items por página
        org.springframework.data.mongodb.core.query.Query dynamicQuery = new Query().with(pageable);

        // 2. Construir criterios dinámicos
        Criteria criteria = new Criteria();

        // Excluir DELETED si no es admin
        if (!isAdmin) {
            criteria.and("status").ne(ReportStatus.DELETED);
        }

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
                ReportStatus statusEnum = ReportStatus.valueOf(status.toUpperCase());
                criteria.and("status").is(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Estado inválido: " + status);
            }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        if(!isAdmin){
            if(reportRepository.findById(id).isPresent()){
                Report report = reportRepository.findById(id).get();
                if(report.getStatus().equals(ReportStatus.DELETED)){
                    throw new NotFoundException("No se encontro el reporte con el id: "+id);
                }
            }else{
                throw new NotFoundException("No se encontro el reporte con el id: "+id);
            }
        }
        Report report = reportRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro el reporte"));
        return reportMapper.toResponse(report) != null ? Optional.of(reportMapper.toResponse(report)) : Optional.empty();
    }

    @Override
    public Optional<ReportResponse> changeReportStatus(String id, ReportStatus status, String userId) {
        if( isValidChangeStatus(userId, status)) {

            Report report = reportRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontro el reporte con el id: " + id));
            report.setStatus(status);
            reportRepository.save(report);

            reportHistoryService.saveReportHistory(report.getId(), userId, "CHANGE_STATUS",
                    "El usuario con el id :" + userId + " cambio el estado del reporte con el id : " + report.getId()
                            + ". Al estado de :" + status
                            + ". En la fecha : " + LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-d|HH:mm:ss")));
            return Optional.of(reportMapper.toResponse(report));
        }else{
            throw new NotPermissionException("No se puede cambiar el estado del reporte, no tienes permisos necesarios");
        }
    }

    private boolean isValidChangeStatus(String userId, ReportStatus status) {
        User user = userRepository.findById(userId).get();
        if(user.getRole().equals(Role.ADMIN)){
            return true;
        }
        return status.equals(ReportStatus.DELETED);
    }


    @Override
    public Optional<ReportResponse> updateReport(String id, List<MultipartFile>newImages,
                                                 List<Integer> imagesToDelete,
                                                 List<Integer>categoriesToDelete, ReportRequest reportRequest,
                                                 String userId) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el reporte con el id: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        if(!isAdmin || report.getUserId().toString().equals(userId)){
            throw new NotPermissionException("No tienes permisos necesarios para modificar el reporte");
        }
        if(report.getStatus().equals(ReportStatus.DELETED)){
            throw new NotFoundException("No se encontro el reporte con el id: "+id);
        }
        report.setTitle(reportRequest.title()!=null ? reportRequest.title() : report.getTitle());

        report.setDescription(reportRequest.description()!=null ? reportRequest.description() : report.getDescription());

        report.setLocation(reportRequest.location()!=null ? reportMapper.locationToGeoJsonPoint(reportRequest.location()) : report.getLocation());

        report.setImages(añadirIamgenesNuevas(report, newImages, imagesToDelete));
        report.setCategories(añadirCategoriasNuevas(report, reportRequest, categoriesToDelete));

        reportHistoryService.saveReportHistory(report.getId(), userId,"MODIFY",
                mensajeUpdateHistorial(report,newImages,imagesToDelete,categoriesToDelete,reportRequest,userId));

        return Optional.of(reportMapper.toResponse(reportRepository.save(report)));
    }

    public String mensajeUpdateHistorial(Report report, List<MultipartFile>newImages,
                                         List<Integer> imagesToDelete,
                                         List<Integer>categoriesToDelete, ReportRequest reportRequest,
                                         String userId){
        String mensajeHistoria = "El usuario con el id :"+userId+" modifico el reporte con el id : "+report.getId();
        if(reportRequest.title() != null){
            mensajeHistoria+=", se cambio el titulo por : "+reportRequest.title();
        }
        if (reportRequest.description() != null){
            report.setDescription(reportRequest.description());
            mensajeHistoria+=", se cambio el descripcion por : "+reportRequest.description();
        }
        if(reportRequest.location()!=null){
            report.setLocation(reportMapper.locationToGeoJsonPoint(reportRequest.location()));
            mensajeHistoria+=", se cambio el location por : "+reportRequest.location().coordinates().toString();
        }
        if(newImages!=null && !newImages.isEmpty()){
            mensajeHistoria+=", se agregaron "+newImages.size()+" imagenes";
        }
        if(reportRequest.categories()!=null && !reportRequest.categories().isEmpty()){
            mensajeHistoria+=", se agregaron "+reportRequest.categories().size()+" categorias";
        }
        if(imagesToDelete!=null && !imagesToDelete.isEmpty()){
            mensajeHistoria+=", se eliminaron "+imagesToDelete.size()+" imagenes";
        }
        if(categoriesToDelete!=null && !categoriesToDelete.isEmpty()){
            mensajeHistoria+=", se eliminaron "+categoriesToDelete.size()+" categorias";
        }
        mensajeHistoria+=". \nEn la fecha : "+LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-d|HH:mm:ss"));
        return mensajeHistoria;
    }

    @Override
    public void deleteReport(String id, String userId) {
        Report auxReport = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el reporte con el id: " + id));
        if(auxReport.getStatus().equals(ReportStatus.DELETED)){
            throw new NotFoundException("No se encontro el reporte con el id: "+id);
        }
        auxReport.setStatus(ReportStatus.DELETED);
        reportHistoryService.saveReportHistory(id, userId, "DELETE",
                "El usuario con el id :"+userId+" elimino el reporte con el id : "+id
                        +". En la fecha : "+LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-d|HH:mm:ss")));
        reportRepository.save(auxReport);
    }

    @Override
    public void addComment(String reportId,Comment comment) {

    }

    @Override
    public void increaseImport(String reportId, String userId) {

        if(reportRepository.findById(reportId).isPresent()){
            Report report = reportRepository.findById(reportId).get();
            comprobarUserGaveImportantExist(report);
            for (String user : report.getUsersGaveImportant()){
                if(user.equals(userId)){
                    report.setImportant(report.getImportant()-1);
                    report.getUsersGaveImportant().remove(user);
                    reportRepository.save(report);
                    return;
                }
            }
            report.setImportant(report.getImportant()+1);
            report.getUsersGaveImportant().add(userId);
            reportRepository.save(report);
        }else{
            throw new NotFoundException("No se encontro el reporte con el id: "+reportId);
        }
    }

    @Override
    public void increaseIsFake(String reportId, String userId) {
        if(reportRepository.findById(reportId).isPresent()){
            Report report = reportRepository.findById(reportId).get();
            confirmarUsersGaveFakeExist(report);
            for (String user : report.getUsersGaveIsFake()){
                if(user.equals(userId)){
                    report.setIsFake(report.getIsFake()-1);
                    report.getUsersGaveIsFake().remove(user);
                    reportRepository.save(report);
                    return;
                }
            }
            report.setIsFake(report.getIsFake()+1);
            report.getUsersGaveIsFake().add(userId);
            reportRepository.save(report);
        }else{
            throw new NotFoundException("No se encontro el reporte con el id: "+reportId);
        }
    }

    public void confirmarUsersGaveFakeExist(Report report){
        if(report.getUsersGaveIsFake()!=null){
            return;
        }else{
            report.setUsersGaveIsFake(new ArrayList<>());
        }
    }
    public static boolean userIdIsValid(String id) {
        return ObjectId.isValid(id);
    }

    public void comprobarUserGaveImportantExist(Report report){
        if(report.getUsersGaveImportant()!=null){
            return;
        }else{
            report.setUsersGaveImportant(new ArrayList<>());
        }
    }

    public List<Category> añadirCategoriasNuevas(Report report, ReportRequest reportRequest, List<Integer> categoriesToDelete){
        List<Category> categories = report.getCategories();

        if (categoriesToDelete != null && !categoriesToDelete.isEmpty()) {
            categoriesToDelete.sort((o1, o2) -> o2.compareTo(o1));
            for (int index : categoriesToDelete) {
                if (index <= categories.size()) {
                    categories.remove(index);
                }
            }
        }
        if (reportRequest.categories() != null && !reportRequest.categories().isEmpty()) {
            List<Category> newCategories =reportRequest.categories().stream().map(categoryService::getCategoryByName).toList();

            for (Category category : newCategories) {
                if (!categories.contains(category)) {
                    categories.add(category);
                }
            }
        }
        return categories;
    }

    public List<String> añadirIamgenesNuevas(Report report, List<MultipartFile>newImages, List<Integer>imagesToDelete){
        List<String> images = report.getImages();
        if (imagesToDelete != null && !imagesToDelete.isEmpty()) {
            imagesToDelete.sort((o1, o2) -> o2.compareTo(o1));
            for (int index : imagesToDelete) {
                if(index<=images.size()){
                    images.remove(index);
                }
            }
        }
        if (newImages != null && !newImages.isEmpty()) {
            List<String> auxImages = reportMapper.uploadImagesToCloudinary(newImages, cloudinaryService);
            for (int i = 0; i < newImages.size(); i++) {
                if (newImages.get(i)!=null &&!newImages.get(i).isEmpty()) {
                    images.add(auxImages.get(i));
                }
            }
        }
        return  images;
    }

    public boolean reportRequestIsValid(ReportRequest reportRequest){
        return reportRequest.title()!=null && !reportRequest.title().isEmpty()
                && reportRequest.description()!=null && !reportRequest.description().isEmpty()
                && reportRequest.location()!=null
                && reportRequest.categories() != null && !reportRequest.categories().isEmpty();
    }
}