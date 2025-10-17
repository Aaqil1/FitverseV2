package com.fitverse.analytics.controller;

import com.fitverse.analytics.dto.AnalyticsResponse;
import com.fitverse.analytics.dto.CaptureAnalyticsRequest;
import com.fitverse.analytics.service.AnalyticsService;
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
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnalyticsResponse capture(@Valid @RequestBody CaptureAnalyticsRequest request) {
        return service.capture(request);
    }

    @GetMapping("/user/{userId}")
    public List<AnalyticsResponse> latest(@PathVariable Long userId) {
        return service.latestForUser(userId);
    }
}
