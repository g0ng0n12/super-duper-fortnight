package com.playtomic.tests.wallet.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class StripeRestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
        if (statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
            throw new StripeAmountTooSmallException();
        }

        super.handleError(response, statusCode);
    }
}
