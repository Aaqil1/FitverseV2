package com.fitverse.notification.exception;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super("Notification %d not found".formatted(id));
    }
}
