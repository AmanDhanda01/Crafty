package com.amandhanda.projects.Crafty.llm.advisors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import com.amandhanda.projects.Crafty.dto.project.FileNode;
import com.amandhanda.projects.Crafty.service.ProjectFileService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class FileTreeContextAdvisor implements StreamAdvisor {

    private final ProjectFileService projectFileService;

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain chain) {
        Map<String, Object> context = request.context();
        Object projectIdValue = context.get("projectId");
        if (projectIdValue == null) {
            return Flux.error(new IllegalArgumentException("projectId is required for file-tree context"));
        }

        Long projectId = Long.valueOf(projectIdValue.toString());
        List<Message> messages = new ArrayList<>();
        List<Message> incomingMessages = request.prompt().getInstructions();

        incomingMessages.stream()
                .filter(message -> message.getMessageType() == MessageType.SYSTEM)
                .findFirst()
                .ifPresent(messages::add);

        List<FileNode> files = projectFileService.getFileTree(projectId).files();
        messages.add(new SystemMessage("\n\n---- FILE_TREE ----\n" + files));
        incomingMessages.stream()
                .filter(message -> message.getMessageType() != MessageType.SYSTEM)
                .forEach(messages::add);

        ChatClientRequest augmentedRequest = request.mutate()
                .prompt(new Prompt(messages, request.prompt().getOptions()))
                .build();
        return chain.nextStream(augmentedRequest);
    }

    @Override
    public String getName() {
        return "FileTreeContextAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}