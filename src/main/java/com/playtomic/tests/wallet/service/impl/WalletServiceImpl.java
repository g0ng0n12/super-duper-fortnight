package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.api.requests.TransactionHttpRequest;
import com.playtomic.tests.wallet.api.requests.WalletTopUpHttpRequest;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.model.StripeTransaction;
import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.TransactionTypes;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.stripe.StripeService;
import com.playtomic.tests.wallet.util.WalletMappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static com.playtomic.tests.wallet.model.TransactionTypes.valueOf;

@Service
public class WalletServiceImpl implements WalletService {

    private Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private WalletRepository walletRepository;

    private StripeService stripeService;

    @Autowired
    public void setStripeService(StripeService stripeService){ this.stripeService = stripeService; }

    @Autowired
    public void setWalletRepository(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @Override
    public WalletHttpResponse getWalletById(long id) {
        Optional<Wallet> wallet = walletRepository.findById(id);

        return WalletMappers.mapToHttpResponse(getIfWalletExists(id, wallet));
    }

    @Override
    public WalletHttpResponse topUpWallet(long id, WalletTopUpHttpRequest walletTopUpHttpRequest) {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);

        Wallet walletToTopUpEntity = getIfWalletExists(id, optionalWallet);

        StripeTransaction response = stripeService.charge(walletTopUpHttpRequest.getCreditCardNumber(), walletTopUpHttpRequest.getAmount());

        // Adding the try-catch in order to catch errors during the save in the DB, if the DB is not available for example
        // we should refund the money that we charge in strip and cancel the TopUp
        try {
            walletToTopUpEntity.addingBalance(walletTopUpHttpRequest.getAmount());
            walletToTopUpEntity.addTransaction(new Transaction(walletTopUpHttpRequest.getAmount(), TransactionTypes.valueOf("TOPUP"),"CreditCard"));
            walletRepository.save(walletToTopUpEntity);
        } catch (Exception ex){
            log.error("Error while saving the wallet entity in the db... initializing stripe refund for payment id " + response.getId());
            stripeService.refund(response.getId());
            throw new IllegalArgumentException();
        }

        return WalletMappers.mapToHttpResponse(walletToTopUpEntity);
    }

    @Override
    public WalletHttpResponse generateTransaction(long walletId, TransactionHttpRequest request) {

        Wallet walletToUpdate = getIfWalletExists(walletId, walletRepository.findById(walletId));
        TransactionTypes type = valueOf(request.getType());

        switch (type) {
            case SPEND:
                checkIfBalanceIsSufficientForSpending(walletToUpdate.getBalance(),request.getAmount());
                walletToUpdate.subtractingBalance(request.getAmount());
                break;
            case REFUND:
                walletToUpdate.addingBalance(request.getAmount());
                break;
            default:
                throw new IllegalArgumentException();
        }

        walletToUpdate.addTransaction(new Transaction(request.getAmount(), type, request.getProduct()));
        walletRepository.save(walletToUpdate);

        return WalletMappers.mapToHttpResponse(walletToUpdate);
    }


    // Utils/Privates methods
    /*
    * This method checks if the wallet that comes from the bd exists if not we throw an EntityNotFoundException
    */
    private Wallet getIfWalletExists(long id, Optional<Wallet> wallet) {
        if (wallet.isEmpty()) {
            throw new EntityNotFoundException("Wallet with id:" + id + " was not found.");
        }else{
            return wallet.get();
        }
    }

    /*
     * This method checks we have enough balance to spend the amount that we get from the request
     */
    private void checkIfBalanceIsSufficientForSpending(BigDecimal balance, BigDecimal amount) {
        if (balance.subtract(amount).intValue() < 0) {
            throw new IllegalArgumentException();
        }
    }

}
