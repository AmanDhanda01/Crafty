package com.amandhanda.projects.Crafty.dto.member;

import com.amandhanda.projects.Crafty.enums.ProjectRole;

import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(@NotNull ProjectRole role) {
}
