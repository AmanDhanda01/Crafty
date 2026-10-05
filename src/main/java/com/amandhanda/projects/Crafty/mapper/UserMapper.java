package com.amandhanda.projects.Crafty.mapper;

import com.amandhanda.projects.Crafty.dto.auth.SignupRequest;
import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;
import com.amandhanda.projects.Crafty.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(SignupRequest signupRequest);

    UserProfileResponse toUserProfileResponse(User user);

}
