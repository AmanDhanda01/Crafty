package com.amandhanda.projects.Crafty.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.chat.ChatResponse;
import com.amandhanda.projects.Crafty.entity.ChatEvent;
import com.amandhanda.projects.Crafty.entity.ChatMessage;
import com.amandhanda.projects.Crafty.entity.ChatSession;
import com.amandhanda.projects.Crafty.entity.ChatSessionId;
import com.amandhanda.projects.Crafty.entity.Project;
import com.amandhanda.projects.Crafty.entity.User;
import com.amandhanda.projects.Crafty.enums.ChatEventType;
import com.amandhanda.projects.Crafty.enums.MessageRole;
import com.amandhanda.projects.Crafty.error.ResourceNotFoundException;
import com.amandhanda.projects.Crafty.llm.LlmResponseParser;
import com.amandhanda.projects.Crafty.mapper.ChatMapper;
import com.amandhanda.projects.Crafty.repository.ChatEventRepository;
import com.amandhanda.projects.Crafty.repository.ChatMessageRepository;
import com.amandhanda.projects.Crafty.repository.ChatSessionRepository;
import com.amandhanda.projects.Crafty.repository.ProjectRepository;
import com.amandhanda.projects.Crafty.repository.UserRepository;
import com.amandhanda.projects.Crafty.security.AuthUtil;
import com.amandhanda.projects.Crafty.service.ChatService;
import com.amandhanda.projects.Crafty.service.ProjectFileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatEventRepository chatEventRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectFileService projectFileService;
    private final LlmResponseParser llmResponseParser;
    private final AuthUtil authUtil;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public ChatSession getOrCreateChatSession(Long projectId, Long userId) {
        ChatSessionId sessionId = new ChatSessionId(projectId, userId);
        return chatSessionRepository.findById(sessionId).orElseGet(() -> {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project", projectId.toString()));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
            return chatSessionRepository.save(ChatSession.builder()
                    .id(sessionId)
                    .project(project)
                    .user(user)
                    .build());
        });
    }

    @Override
    @Transactional
    public void saveChatTurn(Long projectId, Long userId, String userMessage, String assistantResponse,
            long durationSeconds) {
        ChatSession session = getOrCreateChatSession(projectId, userId);
        chatMessageRepository.save(ChatMessage.builder()
                .chatSession(session)
                .role(MessageRole.USER)
                .content(userMessage)
                .tokensUsed(0)
                .build());

        ChatMessage assistantMessage = chatMessageRepository.save(ChatMessage.builder()
                .chatSession(session)
                .role(MessageRole.ASSISTANT)
                .content(assistantResponse)
                .tokensUsed(0)
                .build());

        List<ChatEvent> events = new ArrayList<>(llmResponseParser.parseChatEvents(assistantResponse, assistantMessage));
        events.addFirst(ChatEvent.builder()
                .chatMessage(assistantMessage)
                .type(ChatEventType.THOUGHT)
                .content("Thought for " + durationSeconds + "s")
                .sequenceOrder(0)
                .build());

        for (ChatEvent event : events) {
            if (event.getType() == ChatEventType.FILE_EDIT && event.getFilePath() != null) {
                projectFileService.saveFile(projectId, event.getFilePath(), event.getContent());
            }
        }
        chatEventRepository.saveAll(events);
    }

    @Override
    @Transactional
    @PreAuthorize("@security.canViewProject(#projectId)")
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        ChatSession session = chatSessionRepository.findById(new ChatSessionId(projectId, userId)).orElse(null);
        if (session == null) {
            return List.of();
        }
        return chatMapper.toChatResponses(chatMessageRepository.findByChatSessionWithEvents(session));
    }
}