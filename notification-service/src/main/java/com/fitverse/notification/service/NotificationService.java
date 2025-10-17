package com.fitverse.notification.service;

import com.fitverse.notification.dto.NotificationRequest;
import com.fitverse.notification.dto.NotificationResponse;
import com.fitverse.notification.entity.Notification;
import com.fitverse.notification.exception.NotificationNotFoundException;
import com.fitverse.notification.mapper.NotificationMapper;
import com.fitverse.notification.repository.NotificationRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    public NotificationService(NotificationRepository repository, NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public NotificationResponse send(NotificationRequest request) {
        Notification saved = repository.save(new Notification(request.getUserId(), request.getMessage()));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> forUser(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public void markAsRead(Long notificationId) {
        Notification notification = repository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
        notification.setRead(true);
        repository.save(notification);
    }
}
