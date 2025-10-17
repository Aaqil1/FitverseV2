package com.fitverse.foodvision.service;

import com.fitverse.foodvision.dto.AnalyzeFoodRequest;
import com.fitverse.foodvision.dto.FoodAnalysisResponse;
import com.fitverse.foodvision.dto.FoodLogRequest;
import com.fitverse.foodvision.dto.FoodLogResponse;
import com.fitverse.foodvision.entity.FoodLog;
import com.fitverse.foodvision.exception.FoodLogNotFoundException;
import com.fitverse.foodvision.mapper.FoodLogMapper;
import com.fitverse.foodvision.repository.FoodLogRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FoodVisionService {

    private final FoodLogRepository repository;
    private final FoodLogMapper mapper;

    public FoodVisionService(FoodLogRepository repository, FoodLogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public FoodAnalysisResponse analyzeFood(AnalyzeFoodRequest request) {
        // Placeholder logic; actual AI integration planned for later phases
        String detectedFood = "Meal from " + request.getImageReference();
        int estimatedCalories = 450;
        return new FoodAnalysisResponse(detectedFood, estimatedCalories);
    }

    public FoodLogResponse logFood(FoodLogRequest request) {
        FoodLog saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<FoodLogResponse> findLogsByUser(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteLog(Long id) {
        if (!repository.existsById(id)) {
            throw new FoodLogNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
