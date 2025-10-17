package com.fitverse.foodvision.dto;

public class FoodAnalysisResponse {

    private String detectedFood;
    private Integer estimatedCalories;

    public FoodAnalysisResponse() {
    }

    public FoodAnalysisResponse(String detectedFood, Integer estimatedCalories) {
        this.detectedFood = detectedFood;
        this.estimatedCalories = estimatedCalories;
    }

    public String getDetectedFood() {
        return detectedFood;
    }

    public void setDetectedFood(String detectedFood) {
        this.detectedFood = detectedFood;
    }

    public Integer getEstimatedCalories() {
        return estimatedCalories;
    }

    public void setEstimatedCalories(Integer estimatedCalories) {
        this.estimatedCalories = estimatedCalories;
    }
}
