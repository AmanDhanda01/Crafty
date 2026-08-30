package com.amandhanda.projects.Crafty.mapper;


import org.mapstruct.Mapper;

import com.amandhanda.projects.Crafty.dto.subscription.PlanResponse;
import com.amandhanda.projects.Crafty.dto.subscription.SubscriptionResponse;
import com.amandhanda.projects.Crafty.entity.Plan;
import com.amandhanda.projects.Crafty.entity.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toSubscriptionResponse(Subscription subscription);

    PlanResponse toPlanResponse(Plan plan);
}

