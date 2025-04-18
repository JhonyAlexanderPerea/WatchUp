package co.uniquindio.dtos.common;

import java.util.List;

import lombok.*;


public record StatusHistoryReport (
        String id,
        List<String> statusHistory){

}
