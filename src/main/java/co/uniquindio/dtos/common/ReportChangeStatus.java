package co.uniquindio.dtos.common;

import co.uniquindio.enums.ReportStatus;
import jakarta.validation.constraints.NotNull;

public record ReportChangeStatus (
        @NotNull
        ReportStatus newStatus
){
}
