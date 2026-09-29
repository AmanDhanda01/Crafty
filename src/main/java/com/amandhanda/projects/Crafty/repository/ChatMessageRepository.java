package com.amandhanda.projects.Crafty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.amandhanda.projects.Crafty.entity.ChatMessage;
import com.amandhanda.projects.Crafty.entity.ChatSession;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("""
            SELECT DISTINCT message FROM ChatMessage message
            LEFT JOIN FETCH message.events
            WHERE message.chatSession = :chatSession
            ORDER BY message.createdAt ASC
            """)
    List<ChatMessage> findByChatSessionWithEvents(@Param("chatSession") ChatSession chatSession);
}