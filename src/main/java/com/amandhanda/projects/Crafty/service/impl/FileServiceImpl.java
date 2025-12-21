package com.amandhanda.projects.Crafty.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.project.FileContentResponse;
import com.amandhanda.projects.Crafty.dto.project.FileNode;
import com.amandhanda.projects.Crafty.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<FileNode> getFileTree(Long projectId, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFileTree'");
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path, Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFileContent'");
    }
       
}
