package com.amandhanda.projects.Crafty.dto.chat;

import com.amandhanda.projects.Crafty.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
