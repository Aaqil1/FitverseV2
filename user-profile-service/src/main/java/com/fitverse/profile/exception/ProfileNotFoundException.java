package com.fitverse.profile.exception;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(Long userId) {
        super("Profile not found for user %d".formatted(userId));
    }
}
