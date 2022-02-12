package com.playtomic.tests.wallet.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletHttpResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("balance")
    private BigDecimal balance;

}
