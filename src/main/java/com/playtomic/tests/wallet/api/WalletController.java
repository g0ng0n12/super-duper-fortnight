package com.playtomic.tests.wallet.api;

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


    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @ApiOperation("Get Wallet by Id.")
    @ResponseStatus(HttpStatus.OK)
    public WalletHttpResponse getWalletById(@ApiParam("Wallet Id") @PathVariable("id") long id) {
        log.debug("Getting Wallet By Id");
        return walletService.getWalletById(id);
    }
}
