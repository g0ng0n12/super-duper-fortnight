package com.playtomic.tests.wallet.api;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.playtomic.tests.wallet.api.responses.TransactionHttpResponse;
import com.playtomic.tests.wallet.api.responses.WalletHttpResponse;
import com.playtomic.tests.wallet.model.Transaction;
import com.playtomic.tests.wallet.model.Wallet;
import com.playtomic.tests.wallet.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;


    private Long walletId;

    @BeforeEach
    public void setup() {
        walletId = 1L;
    }

    @Test
    public void getWalletById() throws Exception{
        Mockito.when(walletService.getWalletById(walletId)).thenReturn(new WalletHttpResponse(walletId, BigDecimal.valueOf(1000),new HashSet<TransactionHttpResponse>()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/{id}", 1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(walletService).getWalletById(1L);
    }

    @Test
    public void getNonExistingWallet() throws Exception{
        Mockito.when(walletService.getWalletById(222222L)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallets/{id}", 222222L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(walletService).getWalletById(222222L);
    }


}
