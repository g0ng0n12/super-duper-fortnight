package com.playtomic.tests.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
// Gon: Using this class in order to deserialize the response that we get from the Stripe Charge HTTP Call
public class StripeTransaction {

    //this represents the paymentId of Stripe
    private String id;

    private BigDecimal amount;
}
