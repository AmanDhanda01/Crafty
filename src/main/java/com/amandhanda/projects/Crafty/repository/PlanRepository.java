package com.amandhanda.projects.Crafty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amandhanda.projects.Crafty.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
   
    Optional<Plan> findById(Long id);

    Optional<Plan> findByStripePriceId(String id);
    
} 
