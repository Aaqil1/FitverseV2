package com.fitverse.fitness.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.fitness.dto.GeneratePlanRequest;
import com.fitverse.fitness.dto.WorkoutLogRequest;
import com.fitverse.fitness.entity.WorkoutLog;
import com.fitverse.fitness.entity.WorkoutPlan;
import com.fitverse.fitness.exception.PlanNotFoundException;
import com.fitverse.fitness.mapper.WorkoutPlanMapper;
import com.fitverse.fitness.repository.WorkoutLogRepository;
import com.fitverse.fitness.repository.WorkoutPlanRepository;
import com.fitverse.shared.ai.RecommendationEngine;
import com.fitverse.shared.ai.dto.WorkoutRecommendation;
import com.fitverse.shared.ai.dto.WorkoutRequestContext;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class WorkoutPlanServiceTest {

    @Mock
    private WorkoutPlanRepository planRepository;

    @Mock
    private WorkoutLogRepository logRepository;

    private final WorkoutPlanMapper mapper = Mappers.getMapper(WorkoutPlanMapper.class);

    @Mock
    private RecommendationEngine recommendationEngine;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private WorkoutPlanService service;

    @BeforeEach
    void setUp() {
        service = new WorkoutPlanService(planRepository, logRepository, mapper, recommendationEngine, kafkaTemplate);
    }

    @Test
    void generatePlanPersistsEntity() {
        GeneratePlanRequest request = new GeneratePlanRequest();
        request.setUserId(1L);
        request.setGoal("LOSE_WEIGHT");
        request.setDurationWeeks(4);
        request.setHeightCm(180.0);
        request.setWeightKg(80.0);

        when(planRepository.save(any(WorkoutPlan.class))).thenAnswer(invocation -> {
            WorkoutPlan plan = invocation.getArgument(0);
            plan.setUserId(1L);
            return plan;
        });
        when(recommendationEngine.recommendPlan(any(WorkoutRequestContext.class)))
                .thenReturn(new WorkoutRecommendation(java.util.List.of("Run"), "Stay hydrated"));

        assertThat(service.generatePlan(request).getUserId()).isEqualTo(1L);
        verify(kafkaTemplate).send(any(), any());
    }

    @Test
    void getLatestPlanThrowsWhenMissing() {
        when(planRepository.findTopByUserIdOrderByStartsOnDesc(1L)).thenReturn(Optional.empty());
        assertThrows(PlanNotFoundException.class, () -> service.getLatestPlan(1L));
    }

    @Test
    void logWorkoutDelegatesToRepository() {
        WorkoutLogRequest request = new WorkoutLogRequest();
        request.setUserId(1L);
        request.setExercise("Run");
        request.setDurationMinutes(30);
        request.setCaloriesBurned(300);

        service.logWorkout(request);
        verify(logRepository).save(any(WorkoutLog.class));
    }

    @Test
    void getWorkoutLogsReturnsData() {
        when(logRepository.findByUserId(1L)).thenReturn(Collections.singletonList(new WorkoutLog(1L, "Run", 30, 300)));
        assertThat(service.getWorkoutLogs(1L)).hasSize(1);
    }
}
