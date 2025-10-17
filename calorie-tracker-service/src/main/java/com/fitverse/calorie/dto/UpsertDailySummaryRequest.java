package com.fitverse.calorie.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class UpsertDailySummaryRequest {

    @NotNull
    @Min(1)
    private Long userId;

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(0)
    private Integer totalCalories;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }
}
