package co.uniquindio.dtos.common;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusHistoryReport {
    private String id;
    private List<String> statusHistory;
}
