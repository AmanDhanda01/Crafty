package com.amandhanda.projects.Crafty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amandhanda.projects.Crafty.entity.ChatEvent;

public interface ChatEventRepository extends JpaRepository<ChatEvent, Long> {
}