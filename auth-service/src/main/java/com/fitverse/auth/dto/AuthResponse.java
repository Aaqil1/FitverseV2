package com.fitverse.auth.dto;

public class AuthResponse {

    private final UserResponse user;
    private final String accessToken;

    public AuthResponse(UserResponse user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public UserResponse getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
