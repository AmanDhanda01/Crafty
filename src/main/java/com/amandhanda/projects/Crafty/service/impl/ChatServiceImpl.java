package com.amandhanda.projects.Crafty.service.impl;

import com.amandhanda.projects.Crafty.dto.chat.ChatResponse;
import com.amandhanda.projects.Crafty.entity.ChatMessage;
import com.amandhanda.projects.Crafty.entity.ChatSession;
import com.amandhanda.projects.Crafty.entity.ChatSessionId;
import com.amandhanda.projects.Crafty.mapper.ChatMapper;
import com.amandhanda.projects.Crafty.repository.ChatMessageRepository;
import com.amandhanda.projects.Crafty.repository.ChatSessionRepository;
import com.amandhanda.projects.Crafty.security.AuthUtil;
import com.amandhanda.projects.Crafty.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final AuthUtil authUtil;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId = authUtil.getCurrentUserId();

        ChatSession chatSession = chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId, userId)
        );

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.fromListOfChatMessage(chatMessageList);
    }
}
