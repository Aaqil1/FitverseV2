package com.fitverse.shared.ai.dto;

public class WorkoutRequestContext {

    private final double bmi;
    private final String goal;

    public WorkoutRequestContext(double bmi, String goal) {
        this.bmi = bmi;
        this.goal = goal;
    }

    public double getBmi() {
        return bmi;
    }

    public String getGoal() {
        return goal;
    }
}
