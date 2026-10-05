package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.subscription.PlanResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface PlanService {
     List<PlanResponse> getAllActivePlans();
}
