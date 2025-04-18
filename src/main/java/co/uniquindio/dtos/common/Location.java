package co.uniquindio.dtos.common;

import java.util.List;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record Location(
        @DefaultValue(value = "Point")
        String type,
        Double[] coordinates){

}
