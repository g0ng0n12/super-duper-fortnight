package com.playtomic.tests.wallet.util;

import com.playtomic.tests.wallet.api.responses.TransactionHttpResponse;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.model.Wallet;

import java.util.stream.Collectors;

public class WalletMappers {
    public static WalletHttpResponse mapToHttpResponse(Wallet wallet) {

        WalletHttpResponse walletHttpResponse = new WalletHttpResponse();
        walletHttpResponse.setBalance(wallet.getBalance());
        walletHttpResponse.setId(wallet.getId());

        walletHttpResponse.setTransactions(
                wallet.getTransactions()
                        .stream()
                        .map(transaction ->
                            new TransactionHttpResponse(transaction.getId(), transaction.getAmount(), transaction.getTransactionType().toString(), transaction.getCreatedAt()))
                        .collect(Collectors.toSet()));

        return walletHttpResponse;
    }
}
