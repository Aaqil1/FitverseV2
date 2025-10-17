package com.fitverse.foodvision.service;

import com.fitverse.foodvision.dto.AnalyzeFoodRequest;
import com.fitverse.foodvision.dto.FoodAnalysisResponse;
import com.fitverse.foodvision.dto.FoodLogRequest;
import com.fitverse.foodvision.dto.FoodLogResponse;
import com.fitverse.foodvision.entity.FoodLog;
import com.fitverse.foodvision.exception.FoodLogNotFoundException;
import com.fitverse.foodvision.mapper.FoodLogMapper;
import com.fitverse.foodvision.repository.FoodLogRepository;
import com.fitverse.shared.ai.FoodInferenceProvider;
import com.fitverse.shared.ai.dto.FoodInferenceResult;
import com.fitverse.shared.messaging.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FoodVisionService {

    private final FoodLogRepository repository;
    private final FoodLogMapper mapper;
    private final FoodInferenceProvider inferenceProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FoodVisionService(FoodLogRepository repository, FoodLogMapper mapper,
            FoodInferenceProvider inferenceProvider, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.inferenceProvider = inferenceProvider;
        this.kafkaTemplate = kafkaTemplate;
    }

    public FoodAnalysisResponse analyzeFood(AnalyzeFoodRequest request) {
        FoodInferenceResult result = inferenceProvider.inferCalories(request.getImageReference());
        return new FoodAnalysisResponse(result.getLabel(), result.getEstimatedCalories());
    }

    public FoodLogResponse logFood(FoodLogRequest request) {
        FoodLog saved = repository.save(mapper.toEntity(request));
        FoodLogResponse response = mapper.toResponse(saved);
        kafkaTemplate.send(KafkaTopics.FOOD_LOG_CREATED, response);
        return response;
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
