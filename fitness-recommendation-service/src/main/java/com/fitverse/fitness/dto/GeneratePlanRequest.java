package com.fitverse.fitness.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GeneratePlanRequest {

    @NotNull
    @Min(1)
    private Long userId;

    @NotBlank
    private String goal;

    @NotNull
    @Min(1)
    private Integer durationWeeks;

    @NotNull
    @Min(1)
    private Double heightCm;

    @NotNull
    @Min(1)
    private Double weightKg;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Integer getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(Integer durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Double heightCm) {
        this.heightCm = heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }
}
