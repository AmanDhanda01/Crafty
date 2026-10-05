package com.amandhanda.projects.Crafty.repository;

import com.amandhanda.projects.Crafty.entity.ChatEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {
}
