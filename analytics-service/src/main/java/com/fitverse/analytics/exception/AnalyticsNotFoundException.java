package com.fitverse.analytics.exception;

public class AnalyticsNotFoundException extends RuntimeException {

    public AnalyticsNotFoundException(Long userId) {
        super("No analytics data available for user %d".formatted(userId));
    }
}
