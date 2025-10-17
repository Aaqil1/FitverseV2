package com.fitverse.fitness.controller;

import com.fitverse.fitness.dto.GeneratePlanRequest;
import com.fitverse.fitness.dto.WorkoutPlanResponse;
import com.fitverse.fitness.dto.WorkoutLogRequest;
import com.fitverse.fitness.entity.WorkoutLog;
import com.fitverse.fitness.service.WorkoutPlanService;
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
@RequestMapping("/plans")
public class WorkoutPlanController {

    private final WorkoutPlanService service;

    public WorkoutPlanController(WorkoutPlanService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutPlanResponse generate(@Valid @RequestBody GeneratePlanRequest request) {
        return service.generatePlan(request);
    }

    @GetMapping("/latest/{userId}")
    public WorkoutPlanResponse latest(@PathVariable Long userId) {
        return service.getLatestPlan(userId);
    }

    @PostMapping("/workouts/log")
    @ResponseStatus(HttpStatus.CREATED)
    public void logWorkout(@Valid @RequestBody WorkoutLogRequest request) {
        service.logWorkout(request);
    }

    @GetMapping("/workouts/{userId}")
    public List<WorkoutLog> logs(@PathVariable Long userId) {
        return service.getWorkoutLogs(userId);
    }
}
