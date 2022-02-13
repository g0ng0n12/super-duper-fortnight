package com.playtomic.tests.wallet.model;

public enum TransactionTypes {
    SPEND("Spend"),
    REFUND("refund"),
    TOPUP("topup");

    private final String getTransactionType;

    TransactionTypes(String transactionType) {
        getTransactionType = transactionType;
    }

    @Override
    public String toString() {
        return getTransactionType;
    }
}
