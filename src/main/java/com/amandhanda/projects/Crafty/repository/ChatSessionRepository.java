package com.amandhanda.projects.Crafty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amandhanda.projects.Crafty.entity.ChatSession;
import com.amandhanda.projects.Crafty.entity.ChatSessionId;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}