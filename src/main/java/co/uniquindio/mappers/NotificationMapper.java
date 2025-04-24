package co.uniquindio.mappers;

import co.uniquindio.dtos.response.NotificationResponse;
import co.uniquindio.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {
    NotificationResponse toResponse(Notification notification);

    List<NotificationResponse> toResponse(List<Notification> notifications);
}
