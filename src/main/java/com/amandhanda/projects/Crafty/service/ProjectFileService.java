package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.project.FileContentResponse;
import com.amandhanda.projects.Crafty.dto.project.FileNode;

import java.util.List;

public interface ProjectFileService {
    List<FileNode> getFileTree(Long projectId);

    FileContentResponse getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String path, String content);
}
