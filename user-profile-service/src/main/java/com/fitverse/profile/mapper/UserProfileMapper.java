package com.fitverse.profile.mapper;

import com.fitverse.profile.dto.ProfileResponse;
import com.fitverse.profile.dto.UpdateProfileRequest;
import com.fitverse.profile.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    ProfileResponse toResponse(UserProfile profile);

    void updateProfile(UpdateProfileRequest request, @MappingTarget UserProfile profile);
}
