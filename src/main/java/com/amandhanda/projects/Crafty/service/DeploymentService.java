package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.deploy.DeployResponse;

public interface DeploymentService {

    DeployResponse deploy(Long projectId);
}
