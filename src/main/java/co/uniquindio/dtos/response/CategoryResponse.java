package co.uniquindio.dtos.response;

import lombok.*;

import java.time.LocalDateTime;

public record CategoryResponse(
        String name,
        String description,
        String id,
        LocalDateTime creationDate
) {

}