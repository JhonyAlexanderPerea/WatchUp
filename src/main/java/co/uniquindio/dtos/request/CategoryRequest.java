package co.uniquindio.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest (
        @NotBlank
    String name,
    @NotBlank
    String description
) {}
