package com.amandhanda.projects.Crafty.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import com.amandhanda.projects.Crafty.dto.project.FileNode;
import com.amandhanda.projects.Crafty.entity.ProjectFile;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {

   List<FileNode> toListOfFileNode(List<ProjectFile> projectFiles);
}
