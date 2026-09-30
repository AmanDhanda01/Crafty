package com.amandhanda.projects.Crafty.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.llm.PromptUtils;
import com.amandhanda.projects.Crafty.llm.advisors.FileTreeContextAdvisor;
import com.amandhanda.projects.Crafty.llm.tools.CodeGenerationTools;
import com.amandhanda.projects.Crafty.security.AuthUtil;
import com.amandhanda.projects.Crafty.service.AiGenerationService;
import com.amandhanda.projects.Crafty.service.ChatService;
import com.amandhanda.projects.Crafty.service.ProjectFileService;
import com.amandhanda.projects.Crafty.service.UsageService;
import com.amandhanda.projects.Crafty.dto.chat.StreamResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiGenerationServiceImpl implements AiGenerationService {
    
    private final ChatClient chatClient;
    private final AuthUtil authUtil;
    private final ProjectFileService projectFileService;
    private final ChatService chatService;
    private final FileTreeContextAdvisor fileTreeContextAdvisor;
    private final UsageService usageService;
    
    @Override
    @PreAuthorize("@security.canEditProject(#projectId)")
    public Flux<StreamResponse> streamResponse(String message, Long projectId) {

        usageService.checkDailyTokensUsage();

        Long userId = authUtil.getCurrentUserId();

        chatService.getOrCreateChatSession(projectId, userId);

        Map<String, Object> advisorParams = Map.of(
            "userId", userId,
            "projectId", projectId
        );

        StringBuilder fullResponseBuffer = new StringBuilder();
        AtomicReference<Usage> usage = new AtomicReference<>();
        long startedAt = System.currentTimeMillis();
        CodeGenerationTools codeGenerationTools = new CodeGenerationTools(projectFileService, projectId);

        return chatClient.prompt()
                .system(PromptUtils.CODE_GENERATION_SYSTEM_PROMPT)
                .user(message)
                .tools(codeGenerationTools)
                .advisors(advisorSpec -> {
                    advisorSpec.params(advisorParams);
                    advisorSpec.advisors(fileTreeContextAdvisor);
                })
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    if (response.getMetadata().getUsage() != null) {
                        usage.set(response.getMetadata().getUsage());
                    }
                    String content = response.getResult().getOutput().getText();
                    if (content != null) {
                        fullResponseBuffer.append(content);
                    }
                })
                .map(response -> new StreamResponse(
                    Objects.requireNonNullElse(response.getResult().getOutput().getText(), "")))
                .concatWith(Flux.defer(() -> {
                    long durationSeconds = (System.currentTimeMillis() - startedAt) / 1000;
                    chatService.saveChatTurn(projectId, userId, message, fullResponseBuffer.toString(), durationSeconds);
                    Usage tokenUsage = usage.get();
                    if (tokenUsage != null && tokenUsage.getTotalTokens() != null) {
                        usageService.recordTokenUsage(userId, tokenUsage.getTotalTokens());
                    }
                    return Flux.empty();
                }))
                .doOnError(error -> log.error("Error during generation or chat persistence for projectId: {}", projectId, error));
    }
}
