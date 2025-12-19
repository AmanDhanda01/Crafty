package com.amandhanda.projects.Crafty.service;


import org.jspecify.annotations.Nullable;

import com.amandhanda.projects.Crafty.dto.subscription.PlanResponse;

import java.util.List;

public interface PlanService {
     List<PlanResponse> getAllActivePlans();
}
