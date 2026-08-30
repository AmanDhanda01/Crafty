package com.amandhanda.projects.Crafty.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.amandhanda.projects.Crafty.entity.Subscription;
import com.amandhanda.projects.Crafty.enums.SubscriptionStatus;

import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /*
    * Get the current active subscription
    * */
    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> statusSet);

    boolean existsByStripeSubscriptionId(String subscriptionId);

    Optional<Subscription> findByStripeSubscriptionId(String gatewaySubscriptionId);
}

