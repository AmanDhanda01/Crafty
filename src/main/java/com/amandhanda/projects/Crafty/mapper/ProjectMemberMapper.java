package com.amandhanda.projects.Crafty.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.amandhanda.projects.Crafty.dto.member.MemberResponse;
import com.amandhanda.projects.Crafty.entity.ProjectMember;
import com.amandhanda.projects.Crafty.entity.User;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {

    @Mapping(target="userId", source="id")
    @Mapping(target = "projectRole", constant = "OWNER")
    MemberResponse toMemberResponseFromOwner(User owner);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "projectRole", source = "role")
    MemberResponse toProjectMemberResponseFromMember(ProjectMember projectMember);
}