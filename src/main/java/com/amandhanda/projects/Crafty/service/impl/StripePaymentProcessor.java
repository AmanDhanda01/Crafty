package com.amandhanda.projects.Crafty.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.subscription.CheckoutRequest;
import com.amandhanda.projects.Crafty.dto.subscription.CheckoutResponse;
import com.amandhanda.projects.Crafty.dto.subscription.PortalResponse;
import com.amandhanda.projects.Crafty.entity.Plan;
import com.amandhanda.projects.Crafty.entity.User;
import com.amandhanda.projects.Crafty.error.ResourceNotFoundException;
import com.amandhanda.projects.Crafty.repository.PlanRepository;
import com.amandhanda.projects.Crafty.repository.UserRepository;
import com.amandhanda.projects.Crafty.security.AuthUtil;
import com.amandhanda.projects.Crafty.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class StripePaymentProcessor implements PaymentProcessor {

    final AuthUtil authUtil;
    final PlanRepository planRepository;
    final UserRepository userRepository;

    @Value("${client.url}")
    private String frontendUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan", request.planId().toString()));
        Long userId = authUtil.getCurrentUserId();
        
         User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user", userId.toString()));

        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString());

        try {
            String stripeCustomerId = user.getStripeCustomerId();
            if(stripeCustomerId == null || stripeCustomerId.isEmpty()) {
                params.setCustomerEmail(user.getUsername());
            } else {
                params.setCustomer(stripeCustomerId); // stripe customer Id
            }
            Session session = Session.create(params.build()); // making api call to the Stripe Backend
            return new CheckoutResponse(session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe checkout session", e);
        }
    }

    @Override
    public PortalResponse openCustomerPortal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openCustomerPortal'");
    }

      @Override
    public void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata) {
        log.debug("Handling stripe event: {}", type);

        // switch (type) {
        //     case "checkout.session.completed" -> handleCheckoutSessionCompleted((Session) stripeObject, metadata); // one-time, on checkout completed
        //     case "customer.subscription.updated" -> handleCustomerSubscriptionUpdated((Subscription) stripeObject); // when user cancels, upgrades or any updates
        //     case "customer.subscription.deleted" -> handleCustomerSubscriptionDeleted((Subscription) stripeObject); // when subscription ends, revoke the access
        //     case "invoice.paid" -> handleInvoicePaid((Invoice) stripeObject); // when invoice is paid
        //     case "invoice.payment_failed" -> handleInvoicePaymentFailed((Invoice) stripeObject); // when invoice is not paid, mark as PAST_DUE
        //     default -> log.debug("Ignoring the event: {}", type);
        // }
    }

}
