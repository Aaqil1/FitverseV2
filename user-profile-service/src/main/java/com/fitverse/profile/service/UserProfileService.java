package com.fitverse.profile.service;

import com.fitverse.profile.dto.ProfileResponse;
import com.fitverse.profile.dto.UpdateProfileRequest;
import com.fitverse.profile.entity.UserProfile;
import com.fitverse.profile.exception.ProfileNotFoundException;
import com.fitverse.profile.mapper.UserProfileMapper;
import com.fitverse.profile.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository repository;
    private final UserProfileMapper mapper;

    public UserProfileService(UserProfileRepository repository, UserProfileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long userId) {
        UserProfile profile = repository.findById(userId)
                .orElseThrow(() -> new ProfileNotFoundException(userId));
        return mapper.toResponse(profile);
    }

    public ProfileResponse upsertProfile(UpdateProfileRequest request) {
        UserProfile profile = repository.findById(request.getUserId())
                .orElseGet(() -> new UserProfile(request.getUserId()));
        mapper.updateProfile(request, profile);
        UserProfile saved = repository.save(profile);
        return mapper.toResponse(saved);
    }
}
