package com.fitverse.fitness.exception;

public class PlanNotFoundException extends RuntimeException {

    public PlanNotFoundException(Long userId) {
        super("No workout plan available for user %d".formatted(userId));
    }
}
