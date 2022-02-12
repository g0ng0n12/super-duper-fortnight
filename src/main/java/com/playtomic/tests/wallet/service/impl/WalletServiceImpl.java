package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.util.WalletMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    @Autowired
    public void setWalletRepository(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletHttpResponse getWalletById(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);

        if (wallet.isEmpty()) {
            throw new EntityNotFoundException("Wallet with id:" + id + " was not found.");
        }

        return WalletMappers.mapToHttpResponse(wallet.get());
    }
}
