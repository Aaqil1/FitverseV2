package com.fitverse.fitness.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkoutLogRequest {

    @NotNull
    @Min(1)
    private Long userId;

    @NotBlank
    private String exercise;

    @NotNull
    @Min(1)
    private Integer durationMinutes;

    @NotNull
    @Min(0)
    private Integer caloriesBurned;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(Integer caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}
