package co.uniquindio.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public record CommentRequest(
        @NotNull
        String reportId,
         @NotBlank
         String comment
) {

}
