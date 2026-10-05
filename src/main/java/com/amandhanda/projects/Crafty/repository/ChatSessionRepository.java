package com.amandhanda.projects.Crafty.repository;

import com.amandhanda.projects.Crafty.entity.ChatSession;
import com.amandhanda.projects.Crafty.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}
