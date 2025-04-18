package co.uniquindio.dtos.common;

import java.util.List;

public record StatusHistoryReport (
    String id,
    List<String> statusHistory
){}
