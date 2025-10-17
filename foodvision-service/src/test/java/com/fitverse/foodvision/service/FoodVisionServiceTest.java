package com.fitverse.foodvision.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fitverse.foodvision.dto.AnalyzeFoodRequest;
import com.fitverse.foodvision.dto.FoodLogRequest;
import com.fitverse.foodvision.entity.FoodLog;
import com.fitverse.foodvision.exception.FoodLogNotFoundException;
import com.fitverse.foodvision.mapper.FoodLogMapper;
import com.fitverse.foodvision.repository.FoodLogRepository;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FoodVisionServiceTest {

    @Mock
    private FoodLogRepository repository;

    private final FoodLogMapper mapper = Mappers.getMapper(FoodLogMapper.class);

    private FoodVisionService service;

    @BeforeEach
    void setUp() {
        service = new FoodVisionService(repository, mapper);
    }

    @Test
    void analyzeReturnsPlaceholder() {
        AnalyzeFoodRequest request = new AnalyzeFoodRequest();
        request.setImageReference("image.png");
        request.setMealType("LUNCH");
        request.setUserId(1L);

        assertThat(service.analyzeFood(request).getDetectedFood()).contains("image.png");
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
}
