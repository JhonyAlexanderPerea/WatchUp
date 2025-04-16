package co.uniquindio.dtos.response;

import co.uniquindio.enums.ReportStatus;
import co.uniquindio.model.Category;
import co.uniquindio.model.Location;

import java.util.List;
import java.time.LocalDate;

public class ReportDetailResponse {
    private String id;
    private String title;
    private String description;
    private ReportStatus status;
    private int importantCount;
    private boolean isImportant;
    private Location location;
    private List<Category> categories;
    private LocalDate createdAt;
    private BasicUserInfoResponse createdBy;
    private List<String> images;
    private int commentsCount;
    private List<StatusHistoryItemResponse> statusHistory;
}
