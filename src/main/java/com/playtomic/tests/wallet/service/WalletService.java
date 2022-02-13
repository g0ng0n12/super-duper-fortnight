package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.api.requests.TransactionHttpRequest;
import com.playtomic.tests.wallet.api.requests.WalletTopUpHttpRequest;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;

public interface WalletService {

    WalletHttpResponse getWalletById (long id);

    WalletHttpResponse topUpWallet(long id, WalletTopUpHttpRequest walletTopUpHttpRequest);

    WalletHttpResponse generateTransaction(long walletId, TransactionHttpRequest request);

}
