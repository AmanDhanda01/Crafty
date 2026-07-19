package com.amandhanda.projects.Crafty.service.impl;

import org.springframework.stereotype.Service;

import com.amandhanda.projects.Crafty.dto.subscription.CheckoutRequest;
import com.amandhanda.projects.Crafty.dto.subscription.CheckoutResponse;
import com.amandhanda.projects.Crafty.dto.subscription.PortalResponse;
import com.amandhanda.projects.Crafty.dto.subscription.SubscriptionResponse;
import com.amandhanda.projects.Crafty.service.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Override
    public SubscriptionResponse getCurrentSubscription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentSubscription'");
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCheckoutSessionUrl'");
    }

    @Override
    public PortalResponse openCustomerPortal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openCustomerPortal'");
    }

}
