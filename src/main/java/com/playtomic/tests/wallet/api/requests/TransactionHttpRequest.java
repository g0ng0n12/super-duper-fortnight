package com.playtomic.tests.wallet.api.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@ApiModel(description = "Class representing the Http Request body for the Generate Transaction.")
@Data
@AllArgsConstructor
public class TransactionHttpRequest {

    @ApiModelProperty(notes = "The Type of Transaction", example = "Spend", required = true, position = 4)
    private String type;

    @ApiModelProperty(notes = "Amount to Spend or Refund to the balance", example = "32", required = true, position = 4)
    private BigDecimal amount;

    @ApiModelProperty(notes = "Product", example = "rent", required = true, position = 4)
    private String product;
}
