package com.amandhanda.projects.Crafty.entity;

import java.time.Instant;

import com.amandhanda.projects.Crafty.enums.SubscriptionStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Subscription {
   
    Long id;

    User user;

    Plan plan;

    SubscriptionStatus status;

    String stripeSubscriptionId;
    String stripeCustomerId;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    // Boolean cancelAtPeriodEnd = false;
    
    Instant createdAt;
    Instant updatedAt;


}
