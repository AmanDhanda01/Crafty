package com.amandhanda.projects.Crafty.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.project.ProjectRequest;
import com.amandhanda.projects.Crafty.dto.project.ProjectResponse;
import com.amandhanda.projects.Crafty.dto.project.ProjectSummaryResponse;
import com.amandhanda.projects.Crafty.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProjects'");
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserProjectById'");
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProject'");
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProject'");
    }

    @Override
    public void softDelete(Long id, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'softDelete'");
    }

}
