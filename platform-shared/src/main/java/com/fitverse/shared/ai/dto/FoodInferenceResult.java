package com.fitverse.shared.ai.dto;

public class FoodInferenceResult {

    private final String label;
    private final int estimatedCalories;

    public FoodInferenceResult(String label, int estimatedCalories) {
        this.label = label;
        this.estimatedCalories = estimatedCalories;
    }

    public String getLabel() {
        return label;
    }

    public int getEstimatedCalories() {
        return estimatedCalories;
    }
}
