package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.stripe.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.stripe.StripeServiceException;
import com.playtomic.tests.wallet.service.stripe.StripeService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * This test is failing with the current implementation.
 *
 * How would you test this?
 * I added a MockRestServiceServer to mock the Stripe Server call and then use the expect and respond to mock
 * the response that I want to expect from the mock server
 */
public class StripeServiceTest {

    @Mock
    private MockRestServiceServer mockServer;

    private StripeService s;

    URI testUri = URI.create("http://how-would-you-test-me.localhost");

    @BeforeEach
    void setup() {
        s = new StripeService(testUri, testUri, new RestTemplateBuilder());
        mockServer = MockRestServiceServer.createServer(s.getRestTemplate());
    }


    @Test
    public void test_exception() {
        mockServer.expect(requestTo(testUri)).andRespond((response) -> {
            throw new StripeAmountTooSmallException();
        });
        Assertions.assertThrows(StripeAmountTooSmallException.class, () -> {
            s.charge("4242 4242 4242 4242", new BigDecimal(5));
        });
    }

    @Test
    public void test_ok() throws StripeServiceException {
        mockServer.expect(requestTo(testUri)).andRespond(withSuccess());
        s.charge("4242 4242 4242 4242", new BigDecimal(15));
    }
}
