package co.uniquindio.dtos.common;

import java.util.List;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Builder
public record Location(
        @DefaultValue(value = "Point")
        String type,
        List<Double>coordinates){

}
