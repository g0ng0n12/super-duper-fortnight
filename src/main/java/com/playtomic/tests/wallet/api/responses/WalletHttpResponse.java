package com.playtomic.tests.wallet.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.playtomic.tests.wallet.model.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletHttpResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("transactions")
    private Set<TransactionHttpResponse> transactions;

}
