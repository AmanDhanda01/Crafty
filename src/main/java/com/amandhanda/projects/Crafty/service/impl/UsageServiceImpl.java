package com.amandhanda.projects.Crafty.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.subscription.PlanResponse;
import com.amandhanda.projects.Crafty.dto.subscription.PlanLimitsResponse;
import com.amandhanda.projects.Crafty.dto.subscription.SubscriptionResponse;
import com.amandhanda.projects.Crafty.dto.subscription.UsageTodayResponse;
import com.amandhanda.projects.Crafty.entity.UsageLog;
import com.amandhanda.projects.Crafty.repository.UsageLogRepository;
import com.amandhanda.projects.Crafty.security.AuthUtil;
import com.amandhanda.projects.Crafty.service.SubscriptionService;
import com.amandhanda.projects.Crafty.service.UsageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    private static final int FREE_TIER_DAILY_TOKEN_LIMIT = 10_000;
    private static final int FREE_TIER_PROJECT_LIMIT = 100;
    private static final ZoneId USAGE_ZONE = ZoneId.of("Asia/Kolkata");

    private final UsageLogRepository usageLogRepository;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;

    @Override
    public UsageTodayResponse getTodayUsageOfUser() {
        Long userId = authUtil.getCurrentUserId();
        int tokensUsed = getTodayLog(userId).map(UsageLog::getTokensUsed).orElse(0);
        PlanResponse plan = getCurrentPlan();
        int tokensLimit = plan == null || plan.maxTokensPerDay() == null
                ? FREE_TIER_DAILY_TOKEN_LIMIT
                : plan.maxTokensPerDay();
        int previewsLimit = plan == null || plan.maxPreviews() == null ? 0 : plan.maxPreviews();

        return new UsageTodayResponse(tokensUsed, tokensLimit, 0, previewsLimit);
    }

    @Override
    public PlanLimitsResponse getCurrentSubscriptionLimitsOfUser() {
        PlanResponse plan = getCurrentPlan();
        if (plan == null) {
            return new PlanLimitsResponse("Free", FREE_TIER_DAILY_TOKEN_LIMIT, FREE_TIER_PROJECT_LIMIT, false);
        }

        return new PlanLimitsResponse(
                plan.name(),
                plan.maxTokensPerDay(),
                plan.maxProjects(),
                Boolean.TRUE.equals(plan.unlimitedAi()));
    }

    @Override
    public void recordTokenUsage(Long userId, int actualTokens) {
        if (actualTokens <= 0) {
            return;
        }

        LocalDate today = LocalDate.now(USAGE_ZONE);
        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today)
                .orElseGet(() -> UsageLog.builder().userId(userId).date(today).tokensUsed(0).build());
        todayLog.setTokensUsed(todayLog.getTokensUsed() + actualTokens);
        usageLogRepository.save(todayLog);
    }

    @Override
    public void checkDailyTokensUsage() {
        PlanLimitsResponse limits = getCurrentSubscriptionLimitsOfUser();
        if (Boolean.TRUE.equals(limits.unlimitedAi())) {
            return;
        }

        int tokensUsed = getTodayLog(authUtil.getCurrentUserId()).map(UsageLog::getTokensUsed).orElse(0);
        int tokenLimit = limits.maxTokensPerDay() == null
            ? FREE_TIER_DAILY_TOKEN_LIMIT
            : limits.maxTokensPerDay();
        if (tokensUsed >= tokenLimit) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.TOO_MANY_REQUESTS,
                    "Daily AI token limit reached");
        }
    }

    private java.util.Optional<UsageLog> getTodayLog(Long userId) {
        return usageLogRepository.findByUserIdAndDate(userId, LocalDate.now(USAGE_ZONE));
    }

    private PlanResponse getCurrentPlan() {
        SubscriptionResponse subscription = subscriptionService.getCurrentSubscription();
        return subscription == null ? null : subscription.plan();
    }
}
