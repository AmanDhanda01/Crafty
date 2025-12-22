package com.amandhanda.projects.Crafty.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.amandhanda.projects.Crafty.dto.auth.UserProfileResponse;
import com.amandhanda.projects.Crafty.dto.project.ProjectResponse;
import com.amandhanda.projects.Crafty.dto.project.ProjectSummaryResponse;
import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.User;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectResponse toProjectResponse(Project project);
    List<ProjectSummaryResponse> toProjectSummaryResponse(List<Project> projects);
    UserProfileResponse map(User value);
}
