package co.uniquindio.dtos.request;

import lombok.*;

public record CommentRequest(
        String reportId,
         String comment
) {

}
