package com.fitverse.profile.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fitverse.profile.dto.UpdateProfileRequest;
import com.fitverse.profile.entity.UserProfile;
import com.fitverse.profile.exception.ProfileNotFoundException;
import com.fitverse.profile.mapper.UserProfileMapper;
import com.fitverse.profile.repository.UserProfileRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

    @Mock
    private UserProfileRepository repository;

    private final UserProfileMapper mapper = Mappers.getMapper(UserProfileMapper.class);

    private UserProfileService service;

    @BeforeEach
    void setUp() {
        service = new UserProfileService(repository, mapper);
    }

    @Test
    void getProfileThrowsWhenMissing() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProfileNotFoundException.class, () -> service.getProfile(1L));
    }

    @Test
    void upsertCreatesNewProfile() {
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setUserId(2L);
        request.setHeightCm(180);
        request.setWeightKg(75);

        when(repository.findById(2L)).thenReturn(Optional.empty());
        when(repository.save(any(UserProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThat(service.upsertProfile(request).getHeightCm()).isEqualTo(180);
    }
}
