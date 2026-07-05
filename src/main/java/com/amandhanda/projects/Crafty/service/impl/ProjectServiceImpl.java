package com.amandhanda.projects.Crafty.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.project.ProjectRequest;
import com.amandhanda.projects.Crafty.dto.project.ProjectResponse;
import com.amandhanda.projects.Crafty.dto.project.ProjectSummaryResponse;
import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.ProjectMember;
import com.amandhanda.projects.Crafty.entity.ProjectMemberId;
import com.amandhanda.projects.Crafty.entity.User;
import com.amandhanda.projects.Crafty.enums.ProjectRole;
import com.amandhanda.projects.Crafty.error.ResourceNotFoundException;
import com.amandhanda.projects.Crafty.mapper.ProjectMapper;
import com.amandhanda.projects.Crafty.repository.ProjectMemberRepository;
import com.amandhanda.projects.Crafty.repository.ProjectRespository;
import com.amandhanda.projects.Crafty.repository.UserRepository;
import com.amandhanda.projects.Crafty.service.ProjectService;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@Transactional
public class ProjectServiceImpl implements ProjectService {


    ProjectRespository projectRespository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    ProjectMemberRepository projectMemberRepository;
    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));

        Project project = Project.builder().name(request.name()).isPublic(false).build();
        project = projectRespository.save(project);

        ProjectMemberId projectMemberId = new ProjectMemberId(user.getId(),project.getId());
        ProjectMember projectMember = ProjectMember.builder().id(projectMemberId).project(project).user(user).role(ProjectRole.OWNER).acceptedAt(Instant.now()).invitedAt(Instant.now()).build();
        projectMemberRepository.save(projectMember);
        return projectMapper.toProjectResponse(project);

    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        List<Project> projects = projectRespository.findAllAccessibleByUser(userId);
        return projectMapper.toProjectSummaryResponse(projects);
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
      Project project = getAccessibleProjectById(id, userId);
      return projectMapper.toProjectResponse(project);
    }


    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
         Project project = getAccessibleProjectById(id, userId);

         project.setName(request.name());
         project = projectRespository.save(project);
         return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);

        project.setDeletedAt(Instant.now());
        projectRespository.save(project);
    }

    // INTERNAL METHODS
    private Project getAccessibleProjectById(Long id, Long userId) {
        return projectRespository.findAccessibleProjectById(id, userId).orElseThrow(() -> new ResourceNotFoundException("Project", id.toString()));
    }

}
