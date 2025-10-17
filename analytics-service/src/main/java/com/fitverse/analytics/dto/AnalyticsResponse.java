package com.fitverse.analytics.dto;

import java.time.Instant;

public class AnalyticsResponse {

    private Long userId;
    private Double bmi;
    private Integer caloriesDelta;
    private Instant capturedAt;

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

    public Instant getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(Instant capturedAt) {
        this.capturedAt = capturedAt;
    }
}
