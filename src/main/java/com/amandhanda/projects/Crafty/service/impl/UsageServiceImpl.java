package com.amandhanda.projects.Crafty.service.impl;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.subscription.PlanLimitsResponse;
import com.amandhanda.projects.Crafty.dto.subscription.UsageTodayResponse;
import com.amandhanda.projects.Crafty.service.UsageService;

@Service
public class UsageServiceImpl implements UsageService {

    @Override
    public UsageTodayResponse getTodayUsageOfUser(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTodayUsageOfUser'");
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentSubscriptionLimitsOfUser'");
    }

}
