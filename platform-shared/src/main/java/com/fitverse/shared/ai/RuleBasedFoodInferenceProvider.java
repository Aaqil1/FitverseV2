package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.FoodInferenceResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@ConditionalOnMissingBean(TensorFlowFoodInferenceProvider.class)
public class RuleBasedFoodInferenceProvider implements FoodInferenceProvider {

    @Override
    public FoodInferenceResult inferCalories(String imageReference) {
        String label = imageReference.contains("salad") ? "Salad" : "Meal";
        int calories = label.equals("Salad") ? 250 : 600;
        return new FoodInferenceResult(label, calories);
    }
}
