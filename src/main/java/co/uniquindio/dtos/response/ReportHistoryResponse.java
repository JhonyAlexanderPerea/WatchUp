package co.uniquindio.dtos.response;

import java.time.LocalDateTime;

public record ReportHistoryResponse (
        String id,
        String reportId,
        String action,
        String description,
        LocalDateTime reportDateCreation,
        String modifiedBy,
        String reportTitle,
        LocalDateTime modifiedDate
){}
