package com.amandhanda.projects.Crafty.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amandhanda.projects.Crafty.dto.subscription.PlanLimitsResponse;
import com.amandhanda.projects.Crafty.dto.subscription.UsageTodayResponse;
import com.amandhanda.projects.Crafty.service.UsageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usage")
public class UsageController {

    private final UsageService usageService;

    @GetMapping("/today")
    public ResponseEntity<UsageTodayResponse> getTodayUsage() {
        return ResponseEntity.ok(usageService.getTodayUsageOfUser());
    }

    @GetMapping("/limits")
    public ResponseEntity<PlanLimitsResponse> getPlanLimits() {
        return ResponseEntity.ok(usageService.getCurrentSubscriptionLimitsOfUser());
    }
}
