package co.uniquindio.serviceImpl;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.dtos.response.PaginatedCommentResponse;
import co.uniquindio.enums.CommentStatus;
import co.uniquindio.enums.ReportStatus;
import co.uniquindio.mappers.CommentMapper;
import co.uniquindio.model.Comment;
import co.uniquindio.model.Report;
import co.uniquindio.repository.CommentRepository;
import co.uniquindio.repository.ReportRepository;
import co.uniquindio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;
    @Override
    public CommentResponse createComment(CommentRequest commentRequest, String userId) {
        Comment comment = commentMapper.parseOf(commentRequest);
        comment.setReportId(commentRequest.reportId());
        comment.setUserId(userId);
        Report report =reportRepository.findById(commentRequest.reportId())
                .orElseThrow(()->new RuntimeException("No se encontro el reporte con el id: "+commentRequest.reportId()));
        report.getComments().add(comment);
        reportRepository.save(report);

        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public Optional<CommentResponse> getComment(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteComment(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontro el comentario con el id: "+id));
        comment.setStatus(CommentStatus.DELETED);
        commentRepository.save(comment);
    }

    @Override
    public Optional<CommentResponse> updateComment(String id, CommentRequest commentRequest) {
        return Optional.empty();
    }

    @Override
    public Optional<CommentResponse> changeCommentStatus(String id, String status) {
        return Optional.empty();
    }

    @Override
    public PaginatedCommentResponse getAllComments(String reportId, String userId, LocalDate creationDate,
                                                   String status, String order, int page) {

        Pageable pageable = PageRequest.of(page, 10, parseSort(order)); // 10 items por página
        org.springframework.data.mongodb.core.query.Query dynamicQuery = new Query().with(pageable);

        // 2. Construir criterios dinámicos
        Criteria criteria = new Criteria();

        if (reportId != null && !reportId.isEmpty()) {
            criteria.and("title").regex(reportId, "i"); // Búsqueda case-insensitive
        }

        if (userId != null && !userId.isEmpty()) {
            criteria.and("userId").is(new ObjectId(userId));
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

        dynamicQuery.addCriteria(criteria);

        // 4. Ejecutar consulta paginada
        long total = mongoTemplate.count(dynamicQuery, Comment.class);
        List<Comment> reports = mongoTemplate.find(dynamicQuery, Comment.class);
        List<Comment> comments = new ArrayList<>();
        if(reportId==null || reportId.isEmpty()){
            comments=commentRepository.findAll();
        }else{
            comments= commentRepository.findCommentsByReportId(reportId);
        }

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(commentMapper.toResponse(comment));
        }
        return new PaginatedCommentResponse(commentResponses,
                new PaginatedContent((int)((total+9)/pageable.getPageSize()),
                        (int)total,
                        page,
                        10));
    }
    private Sort parseSort(String order) {
        if (order == null || order.isEmpty()) return Sort.unsorted();
        String[] parts = order.split(":");
        return Sort.by(Sort.Direction.fromString(parts[1]), parts[0]);
    }
}
