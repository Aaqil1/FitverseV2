package com.fitverse.foodvision.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fitverse.foodvision.dto.AnalyzeFoodRequest;
import com.fitverse.foodvision.dto.FoodLogRequest;
import com.fitverse.foodvision.entity.FoodLog;
import com.fitverse.foodvision.exception.FoodLogNotFoundException;
import com.fitverse.foodvision.mapper.FoodLogMapper;
import com.fitverse.foodvision.repository.FoodLogRepository;
import com.fitverse.shared.ai.FoodInferenceProvider;
import com.fitverse.shared.ai.dto.FoodInferenceResult;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class FoodVisionServiceTest {

    @Mock
    private FoodLogRepository repository;

    private final FoodLogMapper mapper = Mappers.getMapper(FoodLogMapper.class);

    @Mock
    private FoodInferenceProvider foodInferenceProvider;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private FoodVisionService service;

    @BeforeEach
    void setUp() {
        service = new FoodVisionService(repository, mapper, foodInferenceProvider, kafkaTemplate);
    }

    @Test
    void analyzeUsesInferenceProvider() {
        AnalyzeFoodRequest request = new AnalyzeFoodRequest();
        request.setImageReference("image.png");
        request.setMealType("LUNCH");
        request.setUserId(1L);

        when(foodInferenceProvider.inferCalories("image.png")).thenReturn(new FoodInferenceResult("Pizza", 500));

        assertThat(service.analyzeFood(request).getDetectedFood()).isEqualTo("Pizza");
    }

    @Test
    void deleteLogThrowsWhenMissing() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(FoodLogNotFoundException.class, () -> service.deleteLog(1L));
    }

    @Test
    void findLogsMapsEntities() {
        when(repository.findByUserId(1L)).thenReturn(Collections.singletonList(new FoodLog(1L, "BREAKFAST", "Oatmeal", 350)));
        assertThat(service.findLogsByUser(1L)).hasSize(1);
    }

    @Test
    void logFoodPublishesKafkaEvent() {
        FoodLogRequest request = new FoodLogRequest();
        request.setUserId(1L);
        request.setFoodName("Oatmeal");
        request.setCalories(300);
        request.setMealType("BREAKFAST");

        when(repository.save(any(FoodLog.class))).thenReturn(new FoodLog(1L, "BREAKFAST", "Oatmeal", 300));

        service.logFood(request);

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(kafkaTemplate).send(any(), captor.capture());
        assertThat(captor.getValue()).isNotNull();
    }
}
