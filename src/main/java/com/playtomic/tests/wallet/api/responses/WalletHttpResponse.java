package com.playtomic.tests.wallet.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletHttpResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("balance")
    private BigDecimal balance;

}
