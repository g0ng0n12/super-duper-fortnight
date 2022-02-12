package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.api.requests.WalletTopUpHttpRequest;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.model.StripeTransaction;
import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.WalletService;
import com.playtomic.tests.wallet.service.stripe.StripeAmountTooSmallException;
import com.playtomic.tests.wallet.service.stripe.StripeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.client.ExpectedCount;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    WalletRepository walletRepository;

    @Mock
    StripeService stripeService;

    @InjectMocks
    WalletServiceImpl walletService;

    @Test
    public void when_get_wallet_it_should_return_wallet(){
        BigDecimal balance = new BigDecimal(100L);
        Wallet walletExpected = new Wallet(1L,balance, new HashSet<Transaction>());
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(walletExpected));

        WalletHttpResponse walletHttpResponse = walletService.getWalletById(1L);

        assertEquals(1L, walletHttpResponse.getId());
        assertEquals(balance, walletHttpResponse.getBalance());
    }

    @Test
    public void when_get_wallet_with_invalid_id_it_should_throw_exception(){
        when(walletRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            walletService.getWalletById(1L);
        });
    }

    @Test
    public void when_topup_wallet_it_should_throw_exception(){

        // initial data
        BigDecimal balance = new BigDecimal(100L);
        BigDecimal mockAmountToCharge = new BigDecimal(20L);
        WalletTopUpHttpRequest topUpHttpRequest = new WalletTopUpHttpRequest("123456", new BigDecimal(20L));
        StripeTransaction stripeChargeResponse = new StripeTransaction("11231231", mockAmountToCharge);
        Wallet walletExpected = new Wallet(1L,balance, new HashSet<Transaction>());

        // when mocks
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(walletExpected));
        Mockito.when(stripeService.charge(topUpHttpRequest.getCreditCardNumber(),mockAmountToCharge)).thenReturn(stripeChargeResponse);
        Mockito.when(walletRepository.save(any(Wallet.class))).thenThrow(new IllegalArgumentException());

        // calling the service
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            walletService.topUpWallet(1L, topUpHttpRequest);
        });

    }

    @Test
    public void when_topup_wallet_it_should_return_wallet_with_new_balance(){
        BigDecimal balance = new BigDecimal(100L);
        BigDecimal mockAmountToCharge = new BigDecimal(20L);
        WalletTopUpHttpRequest topUpHttpRequest = new WalletTopUpHttpRequest("123456", new BigDecimal(20L));

        Wallet walletExpected = new Wallet(1L,balance, new HashSet<Transaction>());
        Mockito.when(walletRepository.findById(1L)).thenReturn(Optional.of(walletExpected));
        Mockito.when(stripeService.charge(topUpHttpRequest.getCreditCardNumber(),mockAmountToCharge)).thenReturn(new StripeTransaction("11231231", mockAmountToCharge));

        WalletHttpResponse walletHttpResponse = walletService.topUpWallet(1L, topUpHttpRequest);

        assertEquals(1L, walletHttpResponse.getId());
        assertEquals(balance.add(mockAmountToCharge), walletHttpResponse.getBalance());
    }
}
