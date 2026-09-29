package com.amandhanda.projects.Crafty.llm.tools;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import com.amandhanda.projects.Crafty.service.ProjectFileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CodeGenerationTools {

    private final ProjectFileService projectFileService;
    private final Long projectId;

    @Tool(name = "read_files", description = "Read files that appear in the FILE_TREE. Supply project-relative paths only.")
    public List<String> readFiles(
            @ToolParam(description = "List of relative file paths, for example ['src/App.tsx']") List<String> paths) {
        List<String> results = new ArrayList<>();

        for (String path : paths) {
            String cleanPath = path.startsWith("/") ? path.substring(1) : path;
            log.info("Requested project file: {}", cleanPath);
            String content = projectFileService.getFileContent(projectId, cleanPath).content();
            results.add(String.format("--- START OF FILE: %s ---\n%s\n--- END OF FILE ---", cleanPath, content));
        }

        return results;
    }
}