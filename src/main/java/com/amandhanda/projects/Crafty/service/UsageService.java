package com.amandhanda.projects.Crafty.service;

import com.amandhanda.projects.Crafty.dto.subscription.PlanLimitsResponse;
import com.amandhanda.projects.Crafty.dto.subscription.UsageTodayResponse;

public interface UsageService {
    UsageTodayResponse getTodayUsageOfUser();

    PlanLimitsResponse getCurrentSubscriptionLimitsOfUser();
}
