package com.fitverse.fitness.service;

import com.fitverse.fitness.dto.GeneratePlanRequest;
import com.fitverse.fitness.dto.WorkoutLogRequest;
import com.fitverse.fitness.dto.WorkoutPlanResponse;
import com.fitverse.fitness.entity.WorkoutLog;
import com.fitverse.fitness.entity.WorkoutPlan;
import com.fitverse.fitness.exception.PlanNotFoundException;
import com.fitverse.fitness.mapper.WorkoutPlanMapper;
import com.fitverse.fitness.repository.WorkoutLogRepository;
import com.fitverse.fitness.repository.WorkoutPlanRepository;
import com.fitverse.shared.ai.RecommendationEngine;
import com.fitverse.shared.ai.dto.WorkoutRecommendation;
import com.fitverse.shared.ai.dto.WorkoutRequestContext;
import com.fitverse.shared.messaging.KafkaTopics;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkoutPlanService {

    private final WorkoutPlanRepository planRepository;
    private final WorkoutLogRepository logRepository;
    private final WorkoutPlanMapper mapper;
    private final RecommendationEngine recommendationEngine;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public WorkoutPlanService(WorkoutPlanRepository planRepository,
                              WorkoutLogRepository logRepository,
                              WorkoutPlanMapper mapper,
                              RecommendationEngine recommendationEngine,
                              KafkaTemplate<String, Object> kafkaTemplate) {
        this.planRepository = planRepository;
        this.logRepository = logRepository;
        this.mapper = mapper;
        this.recommendationEngine = recommendationEngine;
        this.kafkaTemplate = kafkaTemplate;
    }

    public WorkoutPlanResponse generatePlan(GeneratePlanRequest request) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusWeeks(request.getDurationWeeks());
        double heightMeters = request.getHeightCm() / 100.0;
        double bmi = request.getWeightKg() / (heightMeters * heightMeters);
        WorkoutRecommendation recommendation = recommendationEngine
                .recommendPlan(new WorkoutRequestContext(bmi, request.getGoal()));
        String exercises = recommendation.getExercises().stream()
                .map(exercise -> "\"" + exercise + "\"")
                .collect(Collectors.joining(","));
        String planJson = String.format("{\"goal\":\"%s\",\"durationWeeks\":%d,\"exercises\":[%s],\"notes\":\"%s\"}",
                request.getGoal(), request.getDurationWeeks(), exercises, recommendation.getNotes());
        WorkoutPlan plan = new WorkoutPlan(request.getUserId(), planJson, start, end);
        WorkoutPlan saved = planRepository.save(plan);
        WorkoutPlanResponse response = mapper.toResponse(saved);
        kafkaTemplate.send(KafkaTopics.PLAN_GENERATED, response);
        return response;
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
