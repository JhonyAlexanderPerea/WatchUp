package co.uniquindio.controller;

import co.uniquindio.dtos.common.CustomUserDetails;
import co.uniquindio.dtos.request.CommentRequest;
import co.uniquindio.dtos.response.CommentResponse;
import co.uniquindio.dtos.response.PaginatedCommentResponse;
import co.uniquindio.model.User;
import co.uniquindio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest commentRequest,
                                                        @AuthenticationPrincipal UserDetails userDetails){
        User user = ((CustomUserDetails) userDetails).getUser();
        String userId = user.getId();

        var commentResponse = commentService.createComment(commentRequest, userId);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(commentResponse.id()).
                toUri();

        return ResponseEntity.created(location).body(commentResponse);
    }

    @GetMapping
    public PaginatedCommentResponse getAllComments(@RequestParam(required = false) String reportId,
                                                                   @RequestParam(required = false) String userId,
                                                                   @RequestParam(required = false) LocalDate creationDate,
                                                                   @RequestParam(required = false) String status,
                                                                   @RequestParam(required = false) String order,
                                                                   @RequestParam(required = false) Integer page){
        int currentPage = (page != null) ? page : 0;
        return commentService.getAllComments(reportId, userId, creationDate, status, order,currentPage);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable String id){
        commentService.deleteComment(id);
    }


}
