package com.fitverse.notification.mapper;

import com.fitverse.notification.dto.NotificationResponse;
import com.fitverse.notification.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponse toResponse(Notification notification);
}
