package com.amandhanda.projects.Crafty.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amandhanda.projects.Crafty.entity.UsageLog;

public interface UsageLogRepository extends JpaRepository<UsageLog, Long> {

    Optional<UsageLog> findByUserIdAndDate(Long userId, LocalDate date);
}