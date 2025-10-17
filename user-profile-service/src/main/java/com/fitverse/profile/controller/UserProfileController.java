package com.fitverse.profile.controller;

import com.fitverse.profile.dto.ProfileResponse;
import com.fitverse.profile.dto.UpdateProfileRequest;
import com.fitverse.profile.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ProfileResponse getProfile(@RequestParam("userId") Long userId) {
        return service.getProfile(userId);
    }

    @PutMapping("/me")
    public ProfileResponse updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return service.upsertProfile(request);
    }
}
