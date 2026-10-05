package com.amandhanda.projects.Crafty.service.impl;

import com.amandhanda.projects.Crafty.dto.subscription.PlanResponse;
import com.amandhanda.projects.Crafty.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getAllActivePlans() {
        return List.of();
    }
}
