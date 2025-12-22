package com.amandhanda.projects.Crafty.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.project.ProjectRequest;
import com.amandhanda.projects.Crafty.dto.project.ProjectResponse;
import com.amandhanda.projects.Crafty.dto.project.ProjectSummaryResponse;
import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.User;
import com.amandhanda.projects.Crafty.mapper.ProjectMapper;
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
    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Project project = Project.builder().name(request.name()).owner(user).isPublic(false).build();
        project = projectRespository.save(project);
        return projectMapper.toProjectResponse(project);

    }


    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        List<Project> projects = projectRespository.findAllAccessibleByUser(userId);
        return projectMapper.toProjectSummaryResponse(projects);
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        // TODO Auto-generated method stub
      return null;
    }


    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void softDelete(Long id, Long userId) {
        // TODO Auto-generated method stub
        return;
    }

}
