package com.playtomic.tests.wallet.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    private TransactionTypes transactionType;

    private String product;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Wallet wallet;

    public Transaction(BigDecimal amount, TransactionTypes transactionType, String product) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }
}
