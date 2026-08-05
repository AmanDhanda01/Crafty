package com.amandhanda.projects.Crafty.service;

import java.util.Map;

import com.amandhanda.projects.Crafty.dto.subscription.CheckoutRequest;
import com.amandhanda.projects.Crafty.dto.subscription.CheckoutResponse;
import com.amandhanda.projects.Crafty.dto.subscription.PortalResponse;
import com.stripe.model.StripeObject;

public interface PaymentProcessor {
    
    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal();

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
