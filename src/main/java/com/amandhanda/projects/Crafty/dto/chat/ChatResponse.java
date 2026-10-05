package com.amandhanda.projects.Crafty.dto.chat;

import com.amandhanda.projects.Crafty.entity.ChatEvent;
import com.amandhanda.projects.Crafty.entity.ChatSession;
import com.amandhanda.projects.Crafty.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        MessageRole role,
        List<ChatEventResponse> events,
        String content,
        Integer tokensUsed,
        Instant createdAt

) {
}
