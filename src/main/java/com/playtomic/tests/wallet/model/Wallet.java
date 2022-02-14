package com.playtomic.tests.wallet.model;

import com.sun.istack.NotNull;
import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
@EqualsAndHashCode
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Transaction> transactions;

    public Wallet(BigDecimal balance, Set<Transaction> transactions){
        this.balance = balance;
        this.transactions = transactions;
    }

    public void addingBalance(BigDecimal balanceToAdd){
        this.balance = this.balance.add(balanceToAdd);
    }

    public void subtractingBalance(BigDecimal balanceToSub){
        this.balance = this.balance.subtract(balanceToSub);
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        transaction.setWallet(this);
    }
}
