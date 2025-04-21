package co.uniquindio.service;

import co.uniquindio.dtos.common.PaginatedContent;
import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.dtos.response.PaginatedCommentResponse;
import co.uniquindio.mappers.CommentMapper;
import co.uniquindio.model.Comment;
import co.uniquindio.repository.CommentRepository;
import co.uniquindio.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
    @Override
    public CommentResponse createComment(String reportId, CommentRequest commentRequest, String userId) {
        Comment comment = commentMapper.parseOf(commentRequest);
        comment.setReportId(reportId);
        comment.setUserId(userId);
        return commentMapper.toResponse(commentRepository.save(comment));
    }

    @Override
    public Optional<CommentResponse> getComment(String id) {
        return Optional.empty();
    }

    @Override
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
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
    public PaginatedCommentResponse getAllComments(String reportId, String userId, LocalDate creationDate, String status, String order) {
        List<Comment> comments = commentRepository.findCommentsByReportId(reportId);
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(commentMapper.toResponse(comment));
        }
        int totalPages = (commentResponses.size()+9)/10;
        int currentPage = 0;
        int totalElements = commentResponses.size();
        int pageSize = 10;
        PaginatedContent paginatedContent = new PaginatedContent(totalPages, totalElements, currentPage, pageSize);
        return new PaginatedCommentResponse(commentResponses,paginatedContent);
    }
}
