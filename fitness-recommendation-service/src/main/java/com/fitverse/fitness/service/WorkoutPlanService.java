package com.fitverse.fitness.service;

import com.fitverse.fitness.dto.GeneratePlanRequest;
import com.fitverse.fitness.dto.WorkoutPlanResponse;
import com.fitverse.fitness.dto.WorkoutLogRequest;
import com.fitverse.fitness.entity.WorkoutLog;
import com.fitverse.fitness.entity.WorkoutPlan;
import com.fitverse.fitness.exception.PlanNotFoundException;
import com.fitverse.fitness.mapper.WorkoutPlanMapper;
import com.fitverse.fitness.repository.WorkoutLogRepository;
import com.fitverse.fitness.repository.WorkoutPlanRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkoutPlanService {

    private final WorkoutPlanRepository planRepository;
    private final WorkoutLogRepository logRepository;
    private final WorkoutPlanMapper mapper;

    public WorkoutPlanService(WorkoutPlanRepository planRepository,
                              WorkoutLogRepository logRepository,
                              WorkoutPlanMapper mapper) {
        this.planRepository = planRepository;
        this.logRepository = logRepository;
        this.mapper = mapper;
    }

    public WorkoutPlanResponse generatePlan(GeneratePlanRequest request) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusWeeks(request.getDurationWeeks());
        String planJson = "{" + "\"goal\":\"" + request.getGoal() + "\",\"durationWeeks\":" + request.getDurationWeeks() + "}";
        WorkoutPlan plan = new WorkoutPlan(request.getUserId(), planJson, start, end);
        WorkoutPlan saved = planRepository.save(plan);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public WorkoutPlanResponse getLatestPlan(Long userId) {
        WorkoutPlan plan = planRepository.findTopByUserIdOrderByStartsOnDesc(userId)
                .orElseThrow(() -> new PlanNotFoundException(userId));
        return mapper.toResponse(plan);
    }

    public void logWorkout(WorkoutLogRequest request) {
        WorkoutLog log = new WorkoutLog(request.getUserId(), request.getExercise(), request.getDurationMinutes(), request.getCaloriesBurned());
        logRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<WorkoutLog> getWorkoutLogs(Long userId) {
        return logRepository.findByUserId(userId);
    }
}
