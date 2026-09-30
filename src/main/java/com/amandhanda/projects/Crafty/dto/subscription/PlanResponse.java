package com.amandhanda.projects.Crafty.dto.subscription;

public record PlanResponse(
        Long id,
        String name,
        Integer maxProjects,
        Integer maxPreviews,
        Integer maxTokensPerDay,
        Boolean unlimitedAi,
        String price
) {
}
