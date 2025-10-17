package com.fitverse.notification.service;

import com.fitverse.notification.dto.NotificationRequest;
import com.fitverse.notification.dto.NotificationResponse;
import com.fitverse.notification.entity.Notification;
import com.fitverse.notification.exception.NotificationNotFoundException;
import com.fitverse.notification.mapper.NotificationMapper;
import com.fitverse.notification.repository.NotificationRepository;
import com.fitverse.shared.messaging.KafkaTopics;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public NotificationService(NotificationRepository repository, NotificationMapper mapper,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public NotificationResponse send(NotificationRequest request) {
        Notification saved = repository.save(new Notification(request.getUserId(), request.getMessage()));
        NotificationResponse response = mapper.toResponse(saved);
        kafkaTemplate.send(KafkaTopics.NOTIFICATION_SEND, response);
        return response;
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
