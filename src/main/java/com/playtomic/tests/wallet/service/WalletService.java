package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;

public interface WalletService {

    public WalletHttpResponse getWalletById (long id);
}
