package com.fitverse.notification.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.notification.dto.NotificationRequest;
import com.fitverse.notification.entity.Notification;
import com.fitverse.notification.exception.NotificationNotFoundException;
import com.fitverse.notification.mapper.NotificationMapper;
import com.fitverse.notification.repository.NotificationRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    private final NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);

    private NotificationService service;

    @BeforeEach
    void setUp() {
        service = new NotificationService(repository, mapper);
    }

    @Test
    void sendCreatesNotification() {
        NotificationRequest request = new NotificationRequest();
        request.setUserId(1L);
        request.setMessage("Reminder");

        when(repository.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(service.send(request).getMessage()).isEqualTo("Reminder");
    }

    @Test
    void forUserReturnsNotifications() {
        when(repository.findByUserIdOrderByCreatedAtDesc(1L)).thenReturn(Collections.singletonList(new Notification(1L, "Hello")));
        assertThat(service.forUser(1L)).hasSize(1);
    }

    @Test
    void markAsReadThrowsWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotificationNotFoundException.class, () -> service.markAsRead(99L));
    }

    @Test
    void markAsReadUpdatesNotification() {
        Notification notification = new Notification(1L, "Hello");
        when(repository.findById(1L)).thenReturn(Optional.of(notification));

        service.markAsRead(1L);
        verify(repository).save(notification);
        assertThat(notification.isRead()).isTrue();
    }
}
