package com.fitverse.calorie.exception;

public class SummaryNotFoundException extends RuntimeException {

    public SummaryNotFoundException(Long userId) {
        super("Summary not found for user %d".formatted(userId));
    }
}
