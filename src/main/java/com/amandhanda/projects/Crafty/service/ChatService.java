package com.amandhanda.projects.Crafty.service;


import com.amandhanda.projects.Crafty.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getProjectChatHistory(Long projectId);
}
