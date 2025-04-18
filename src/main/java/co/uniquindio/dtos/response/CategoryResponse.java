package co.uniquindio.dtos.response;

import lombok.*;

public record CategoryResponse(
        String name,
        String description,
        String id
) {

}