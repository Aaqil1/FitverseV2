package com.fitverse.analytics.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CaptureAnalyticsRequest {

    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    private Double bmi;

    @NotNull
    private Integer caloriesDelta;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Integer getCaloriesDelta() {
        return caloriesDelta;
    }

    public void setCaloriesDelta(Integer caloriesDelta) {
        this.caloriesDelta = caloriesDelta;
    }
}
