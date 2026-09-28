package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.chat.StreamResponse;

import reactor.core.publisher.Flux;

public interface AiGenerationService {
   Flux<String> streamResponse(String message, Long projectId);
}
