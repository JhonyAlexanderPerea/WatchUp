package co.uniquindio.dtos.common;

import co.uniquindio.enums.ReportStatus;
import co.uniquindio.enums.Status;

public record ReportChangeStatus (
        ReportStatus newStatus
){
}
