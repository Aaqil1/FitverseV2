package com.fitverse.notification.controller;

import com.fitverse.notification.dto.NotificationRequest;
import com.fitverse.notification.dto.NotificationResponse;
import com.fitverse.notification.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse send(@Valid @RequestBody NotificationRequest request) {
        return service.send(request);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> byUser(@PathVariable Long userId) {
        return service.forUser(userId);
    }

    @PostMapping("/{id}/read")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void read(@PathVariable Long id) {
        service.markAsRead(id);
    }
}
