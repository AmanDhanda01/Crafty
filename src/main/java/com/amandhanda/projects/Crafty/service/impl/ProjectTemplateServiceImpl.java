package com.amandhanda.projects.Crafty.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.ProjectFile;
import com.amandhanda.projects.Crafty.error.ResourceNotFoundException;
import com.amandhanda.projects.Crafty.repository.ProjectFileRepository;
import com.amandhanda.projects.Crafty.repository.ProjectRepository;
import com.amandhanda.projects.Crafty.service.ProjectTemplateService;

import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectTemplateServiceImpl implements ProjectTemplateService {

    private final MinioClient minioClient;
    private final ProjectFileRepository projectFileRepository;
    private final ProjectRepository projectRepository;

    @Value("${minio.template-bucket:starter-projects}")
    private String templateBucket;

    @Value("${minio.project-bucket:projects}")
    private String projectBucket;

    @Value("${minio.template-name:react-vite-tailwind-daisyui-starter}")
    private String templateName;

    @Override
    public void initializeProjectFromTemplate(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));

        try {
            if (!minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(templateBucket).build())) {
                log.info("Starter template bucket '{}' is not provisioned; creating project without template files", templateBucket);
                return;
            }

            if (!minioClient.bucketExists(io.minio.BucketExistsArgs.builder().bucket(projectBucket).build())) {
                minioClient.makeBucket(io.minio.MakeBucketArgs.builder().bucket(projectBucket).build());
            }

            Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(templateBucket)
                    .prefix(templateName + "/")
                    .recursive(true)
                    .build());

            List<ProjectFile> files = new ArrayList<>();
            for (Result<Item> objectResult : objects) {
                Item item = objectResult.get();
                if (item.isDir()) {
                    continue;
                }

                String sourceKey = item.objectName();
                String cleanPath = sourceKey.substring((templateName + "/").length());
                String destinationKey = projectId + "/" + cleanPath;

                minioClient.copyObject(CopyObjectArgs.builder()
                        .bucket(projectBucket)
                        .object(destinationKey)
                        .source(CopySource.builder().bucket(templateBucket).object(sourceKey).build())
                        .build());

                ProjectFile file = projectFileRepository.findByProjectIdAndPath(projectId, cleanPath)
                        .orElseGet(() -> ProjectFile.builder()
                                .project(project)
                                .path(cleanPath)
                                .createdAt(Instant.now())
                                .build());
                file.setMinioObjectKey(destinationKey);
                file.setUpdatedAt(Instant.now());
                files.add(file);
            }

            projectFileRepository.saveAll(files);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to initialize project from starter template", exception);
        }
    }
}