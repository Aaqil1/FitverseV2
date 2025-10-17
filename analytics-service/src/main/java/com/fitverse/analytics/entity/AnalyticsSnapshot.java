package com.fitverse.analytics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "analytics_snapshot")
public class AnalyticsSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "bmi", nullable = false)
    private Double bmi;

    @Column(name = "calories_delta", nullable = false)
    private Integer caloriesDelta;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt = Instant.now();

    protected AnalyticsSnapshot() {
    }

    public AnalyticsSnapshot(Long userId, Double bmi, Integer caloriesDelta) {
        this.userId = userId;
        this.bmi = bmi;
        this.caloriesDelta = caloriesDelta;
        this.capturedAt = Instant.now();
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
