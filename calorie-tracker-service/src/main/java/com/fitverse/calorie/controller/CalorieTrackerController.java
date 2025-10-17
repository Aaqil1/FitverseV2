package com.fitverse.calorie.controller;

import com.fitverse.calorie.dto.DailySummaryResponse;
import com.fitverse.calorie.dto.UpsertDailySummaryRequest;
import com.fitverse.calorie.service.CalorieTrackerService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
public class CalorieTrackerController {

    private final CalorieTrackerService service;

    public CalorieTrackerController(CalorieTrackerService service) {
        this.service = service;
    }

    @GetMapping("/daily")
    public DailySummaryResponse getDailySummary(@RequestParam Long userId,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getDailySummary(userId, date);
    }

    @GetMapping("/weekly")
    public List<DailySummaryResponse> getWeeklySummary(@RequestParam Long userId,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.getWeeklySummary(userId, start, end);
    }

    @PostMapping
    public DailySummaryResponse upsert(@Valid @RequestBody UpsertDailySummaryRequest request) {
        return service.upsertSummary(request);
    }
}
