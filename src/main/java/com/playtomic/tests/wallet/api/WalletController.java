package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.api.requests.TransactionHttpRequest;
import com.playtomic.tests.wallet.api.requests.WalletTopUpHttpRequest;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.service.WalletService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private Logger log = LoggerFactory.getLogger(WalletController.class);

    private WalletService walletService;

    @Autowired
    public void setWalletService(WalletService walletService){
        this.walletService = walletService;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation("Create a Wallet with Zero Balance")
    @ResponseStatus(HttpStatus.OK)
    public WalletHttpResponse createWallet() {
        log.debug("Creating a new Wallet");
        return walletService.createWallet();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("Get Wallet by Id.")
    @ResponseStatus(HttpStatus.OK)
    public WalletHttpResponse getWalletById(@ApiParam("Wallet Id") @PathVariable("id") long id) {
        log.debug("Getting Wallet By Id");
        return walletService.getWalletById(id);
    }

    @RequestMapping(path= "{id}/topup", method = RequestMethod.PATCH, produces = "application/json")
    @ApiOperation("Adding money to your wallet using CreditCard")
    @ResponseStatus(HttpStatus.OK)
    public WalletHttpResponse topUpWalletWithCreditCard(@ApiParam("Wallet Id") @PathVariable("id") long id, @RequestBody WalletTopUpHttpRequest request) {
        log.debug("Top up the Wallet By Id");
        topUpRequestValidation(request);
        return walletService.topUpWallet(id, request);
    }

    @RequestMapping(path= "{id}/transactions", method = RequestMethod.PATCH, produces = "application/json")
    @ApiOperation("Adding money to your wallet using CreditCard")
    @ResponseStatus(HttpStatus.OK)
    public WalletHttpResponse generateTransaction(@ApiParam("Wallet Id") @PathVariable("id")long walletId, @RequestBody TransactionHttpRequest request) {
        log.debug("Generate Transaction for Wallet By Id");
        return walletService.generateTransaction(walletId, request);
    }

    private void topUpRequestValidation(WalletTopUpHttpRequest request){
        if (request.getAmount().intValue() < 15){
            throw new IllegalArgumentException();
        }
    }

}
