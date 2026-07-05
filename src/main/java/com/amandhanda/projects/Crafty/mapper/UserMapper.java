package com.amandhanda.projects.Crafty.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.amandhanda.projects.Crafty.dto.auth.SignUpRequest;
import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;
import com.amandhanda.projects.Crafty.entity.User;


@Mapper(componentModel = "spring")
public interface UserMapper {
   @Mapping(target = "id", ignore = true)
   @Mapping(target = "createdAt", ignore = true)
   @Mapping(target = "updatedAt", ignore = true)
   @Mapping(target = "deletedAt", ignore = true)
   User toEntity(SignUpRequest signupRequest);
   UserProfileResponse toUserProfileResponse(User user);
}
