package co.uniquindio.service;

import co.uniquindio.dtos.response.NotificationResponse;
import co.uniquindio.dtos.response.PaginatedNotificationResponse;
import co.uniquindio.model.Report;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {
    @Async("taskExecutor")
    CompletableFuture<Void> makeNotifacationToAll(Report report);
    @PreAuthorize("hasAuthority('ADMIN') or isAuthenticated()")
    PaginatedNotificationResponse getNotifications(String userId, String status);
    @PreAuthorize("hasAuthority('ADMIN') or isAuthenticated()")
    void deleteNotification(String id);
    @PreAuthorize("hasAuthority('ADMIN') or @securityService.isCurrentUser(#userId)")
    NotificationResponse changeNotificationStatus(String id, String userId);
    NotificationResponse getNotification(String id);
}
