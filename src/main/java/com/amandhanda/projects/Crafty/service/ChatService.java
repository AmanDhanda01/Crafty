package com.amandhanda.projects.Crafty.service;

import java.util.List;

import com.amandhanda.projects.Crafty.dto.chat.ChatResponse;
import com.amandhanda.projects.Crafty.entity.ChatSession;

public interface ChatService {

    ChatSession getOrCreateChatSession(Long projectId, Long userId);

    void saveChatTurn(Long projectId, Long userId, String userMessage, String assistantResponse, long durationSeconds);

    List<ChatResponse> getProjectChatHistory(Long projectId);
}