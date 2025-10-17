package com.fitverse.fitness.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "workout_plan")
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "plan_json", nullable = false)
    @Lob
    private String planJson;

    @Column(name = "starts_on")
    private LocalDate startsOn;

    @Column(name = "ends_on")
    private LocalDate endsOn;

    protected WorkoutPlan() {
    }

    public WorkoutPlan(Long userId, String planJson, LocalDate startsOn, LocalDate endsOn) {
        this.userId = userId;
        this.planJson = planJson;
        this.startsOn = startsOn;
        this.endsOn = endsOn;
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

    public String getPlanJson() {
        return planJson;
    }

    public void setPlanJson(String planJson) {
        this.planJson = planJson;
    }

    public LocalDate getStartsOn() {
        return startsOn;
    }

    public void setStartsOn(LocalDate startsOn) {
        this.startsOn = startsOn;
    }

    public LocalDate getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(LocalDate endsOn) {
        this.endsOn = endsOn;
    }
}
