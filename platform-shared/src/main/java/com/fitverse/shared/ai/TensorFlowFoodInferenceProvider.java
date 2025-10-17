package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.FoodInferenceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TensorFlowFoodInferenceProvider implements FoodInferenceProvider {

    private static final Logger log = LoggerFactory.getLogger(TensorFlowFoodInferenceProvider.class);

    private final String modelPath;

    public TensorFlowFoodInferenceProvider(@Value("${fitverse.ai.food.model-path:models/foodvision.pb}") String modelPath) {
        this.modelPath = modelPath;
    }

    @Override
    public FoodInferenceResult inferCalories(String imageReference) {
        log.info("Running TensorFlow inference with model {} for reference {}", modelPath, imageReference);
        // Placeholder values that will be replaced when the actual TensorFlow integration is added
        return new FoodInferenceResult("detected-food", 400);
    }
}
