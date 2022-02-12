package com.playtomic.tests.wallet.api.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(description = "Class representing the Http Request body for the wallet top up functionality.")
@Data
@AllArgsConstructor
public class WalletTopUpHttpRequest {

    @ApiModelProperty(notes = "Credit Card Number", example = "32", required = true, position = 4)
    private String creditCardNumber;

    @ApiModelProperty(notes = "Amount to add to the balance", example = "32", required = true, position = 4)
    private BigDecimal amount;
}
