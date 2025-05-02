package co.uniquindio.controller;

import co.uniquindio.dtos.common.CustomUserDetails;
import co.uniquindio.dtos.response.NotificationResponse;
import co.uniquindio.dtos.response.PaginatedNotificationResponse;
import co.uniquindio.model.User;
import co.uniquindio.serviceImpl.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {
    private final NotificationServiceImpl notificationService;

    @GetMapping
    public PaginatedNotificationResponse getAllNotifications(@AuthenticationPrincipal UserDetails userDetails,
                                                             @RequestParam(required = false) String status){
        User user = ((CustomUserDetails) userDetails).getUser();
        String userId = user.getId();
        return notificationService.getNotifications(userId,status);
    }

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable String id){
        notificationService.deleteNotification(id);
    }

    @PatchMapping("/{id}")
    public Optional<NotificationResponse> changeNotificationStatus(@PathVariable String id){
        return Optional.ofNullable(notificationService.changeNotificationStatus(id));
    }


}
