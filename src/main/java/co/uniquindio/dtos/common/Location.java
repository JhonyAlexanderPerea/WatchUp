package co.uniquindio.dtos.common;

import java.util.List;

import lombok.*;

@Builder
public record Location (
    String type,
    List<Double> coordinates
) {}
