package com.amandhanda.projects.Crafty.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.subscription.PlanResponse;
import com.amandhanda.projects.Crafty.mapper.SubscriptionMapper;
import com.amandhanda.projects.Crafty.repository.PlanRepository;
import com.amandhanda.projects.Crafty.service.PlanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public List<PlanResponse> getAllActivePlans() {
        return planRepository.findByIsActiveTrueOrderByIdAsc().stream()
                .map(subscriptionMapper::toPlanResponse)
                .toList();
    }

}
