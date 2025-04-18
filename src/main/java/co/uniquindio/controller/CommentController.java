package co.uniquindio.controller;

import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.dtos.response.PaginatedCommentResponse;
import co.uniquindio.service.CommentService;
import co.uniquindio.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/reports/{reportId}/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@PathVariable String reportId, @RequestBody CommentRequest commentRequest,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        String userId = userDetails.getUsername();

        var commentResponse = commentService.createComment(reportId, commentRequest, userId);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(commentResponse.id()).
                toUri();

        return ResponseEntity.created(location).body(commentResponse);
    }

    @GetMapping
    public PaginatedCommentResponse getAllComments(@PathVariable String reportId,
                                                                   @RequestParam(required = false) String userId,
                                                                   @RequestParam(required = false) LocalDate creationDate,
                                                                   @RequestParam(required = false) String status,
                                                                   @RequestParam(required = false) String order,
                                                                   @RequestParam(required = false) int page){

        return commentService.getAllComments(reportId, userId, creationDate, status, order);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable String id, @PathVariable String reportId){
        commentService.deleteComment(id);
    }


}
