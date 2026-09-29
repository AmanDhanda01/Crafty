package com.amandhanda.projects.Crafty.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.amandhanda.projects.Crafty.dto.chat.ChatEventResponse;
import com.amandhanda.projects.Crafty.dto.chat.ChatResponse;
import com.amandhanda.projects.Crafty.entity.ChatEvent;
import com.amandhanda.projects.Crafty.entity.ChatMessage;

@Component
public class ChatMapper {

    public List<ChatResponse> toChatResponses(List<ChatMessage> messages) {
        return messages.stream().map(this::toChatResponse).toList();
    }

    private ChatResponse toChatResponse(ChatMessage message) {
        List<ChatEventResponse> events = message.getEvents() == null
                ? List.of()
                : message.getEvents().stream().map(this::toChatEventResponse).toList();

        return new ChatResponse(
                message.getId(),
                message.getRole(),
                events,
                message.getContent(),
                message.getTokensUsed(),
                message.getCreatedAt());
    }

    private ChatEventResponse toChatEventResponse(ChatEvent event) {
        return new ChatEventResponse(
                event.getId(),
                event.getType(),
                event.getSequenceOrder(),
                event.getContent(),
                event.getFilePath(),
                event.getMetadata());
    }
}