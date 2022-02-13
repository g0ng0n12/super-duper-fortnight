package com.playtomic.tests.wallet.api.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHttpResponse {

    private Long id;

    private BigDecimal amount;

    private String type;

    private LocalDateTime createdAt;
}
