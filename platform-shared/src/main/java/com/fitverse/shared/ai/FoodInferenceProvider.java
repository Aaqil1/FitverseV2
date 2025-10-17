package com.fitverse.shared.ai;

import com.fitverse.shared.ai.dto.FoodInferenceResult;

public interface FoodInferenceProvider {

    FoodInferenceResult inferCalories(String imageReference);
}
