package com.amandhanda.projects.Crafty.llm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.amandhanda.projects.Crafty.entity.ChatEvent;
import com.amandhanda.projects.Crafty.entity.ChatMessage;
import com.amandhanda.projects.Crafty.enums.ChatEventType;

@Component
public class LlmResponseParser {

    private static final Pattern TAG_PATTERN = Pattern.compile(
            "<(message|file|tool)([^>]*)>([\\s\\S]*?)</\\1>",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(path|args)=\"([^\"]+)\"");

    public List<ChatEvent> parseChatEvents(String fullResponse, ChatMessage parentMessage) {
        List<ChatEvent> events = new ArrayList<>();
        Matcher tagMatcher = TAG_PATTERN.matcher(fullResponse);
        int sequenceOrder = 1;

        while (tagMatcher.find()) {
            String tagName = tagMatcher.group(1).toLowerCase();
            Map<String, String> attributes = parseAttributes(tagMatcher.group(2));
            String content = tagMatcher.group(3).trim();
            ChatEvent.ChatEventBuilder event = ChatEvent.builder()
                    .chatMessage(parentMessage)
                    .content(content)
                    .sequenceOrder(sequenceOrder++);

            switch (tagName) {
                case "message" -> event.type(ChatEventType.MESSAGE);
                case "file" -> event.type(ChatEventType.FILE_EDIT).filePath(attributes.get("path"));
                case "tool" -> event.type(ChatEventType.TOOL_LOG).metadata(attributes.get("args"));
                default -> throw new IllegalStateException("Unexpected response tag: " + tagName);
            }
            events.add(event.build());
        }

        return events;
    }

    private Map<String, String> parseAttributes(String source) {
        Map<String, String> attributes = new HashMap<>();
        Matcher matcher = ATTRIBUTE_PATTERN.matcher(source);
        while (matcher.find()) {
            attributes.put(matcher.group(1), matcher.group(2));
        }
        return attributes;
    }
}