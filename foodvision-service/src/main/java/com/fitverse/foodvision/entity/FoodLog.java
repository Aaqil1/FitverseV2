package com.fitverse.foodvision.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "food_logs")
public class FoodLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "meal_type", nullable = false)
    private String mealType;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Integer calories;

    @Column(name = "logged_at", nullable = false)
    private Instant loggedAt = Instant.now();

    protected FoodLog() {
    }

    public FoodLog(Long userId, String mealType, String foodName, Integer calories) {
        this.userId = userId;
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
        this.loggedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Instant getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(Instant loggedAt) {
        this.loggedAt = loggedAt;
    }
}
