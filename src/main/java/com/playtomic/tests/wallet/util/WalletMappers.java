package com.playtomic.tests.wallet.util;

import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.model.Wallet;

public class WalletMappers {
    public static WalletHttpResponse mapToHttpResponse(Wallet wallet) {

        WalletHttpResponse walletHttpResponse = new WalletHttpResponse();
        walletHttpResponse.setBalance(wallet.getBalance());
        walletHttpResponse.setId(wallet.getId());

        return walletHttpResponse;
    }
}
