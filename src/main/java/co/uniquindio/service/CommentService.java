package co.uniquindio.service;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.dtos.response.PaginatedCommentResponse;

import java.time.LocalDate;
import java.util.Optional;

public interface CommentService {
    CommentResponse createComment(String reportId, CommentRequest commentRequest, String userId);
    Optional<CommentResponse> getComment(String id);
    void deleteComment(String id);
    Optional<CommentResponse> updateComment(String id, CommentRequest commentRequest);
    Optional<CommentResponse> changeCommentStatus(String id, String status);
    PaginatedCommentResponse getAllComments(String reportId, String userId, LocalDate creationDate, String status, String order);
}
