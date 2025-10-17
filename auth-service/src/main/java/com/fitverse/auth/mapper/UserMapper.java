package com.fitverse.auth.mapper;

import com.fitverse.auth.dto.UserResponse;
import com.fitverse.auth.entity.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(userAccount.getRole().name())")
    UserResponse toResponse(UserAccount userAccount);
}
