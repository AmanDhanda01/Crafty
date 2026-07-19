package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.project.FileContentResponse;
import com.amandhanda.projects.Crafty.dto.project.FileNode;

import java.util.List;

public interface FileService {
    List<FileNode> getFileTree(Long projectId);

    FileContentResponse getFileContent(Long projectId, String path);
}
