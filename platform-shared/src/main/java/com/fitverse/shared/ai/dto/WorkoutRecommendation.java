package com.fitverse.shared.ai.dto;

import java.util.List;

public class WorkoutRecommendation {

    private final List<String> exercises;
    private final String notes;

    public WorkoutRecommendation(List<String> exercises, String notes) {
        this.exercises = exercises;
        this.notes = notes;
    }

    public List<String> getExercises() {
        return exercises;
    }

    public String getNotes() {
        return notes;
    }
}
