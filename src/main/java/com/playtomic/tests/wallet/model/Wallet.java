package com.playtomic.tests.wallet.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Transaction> transactions;


}
