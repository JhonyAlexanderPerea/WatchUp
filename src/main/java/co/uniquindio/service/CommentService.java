package co.uniquindio.service;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.dtos.response.PaginatedCommentResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.Optional;

public interface CommentService {

    CommentResponse createComment(CommentRequest commentRequest, String userId);

    @PreAuthorize("hasAuthority('ADMIN') or @securityService.isCurrentUser(#userId)")
    void deleteComment(String id, String userId);

    PaginatedCommentResponse getAllComments(String reportId, String userId, LocalDate creationDate, String status, String order, int page);
    Optional<CommentResponse> getComment(String id);


    @PreAuthorize("hasAuthority('ADMIN') or @securityService.isCurrentUser(#id)")
    Optional<CommentResponse> updateComment(String id, CommentRequest commentRequest);

    @PreAuthorize("hasAuthority('ADMIN')")
    Optional<CommentResponse> changeCommentStatus(String id, String status);
}
