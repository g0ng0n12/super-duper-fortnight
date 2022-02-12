//package com.playtomic.tests.wallet.api;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//
//@WebMvcTest(WalletController.class)
//public class WalletControllerTest {
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private WalletService walletService;
//
//    private String walletId;
//
//    @BeforeEach
//    public void setup() {
//        walletId = UUID.randomUUID().toString();
//    }
//
//    @Test
//    public void getWalletById() throws Exception{
//        Mockito.when(walletService.getById(walletId)).thenReturn(new Wallet(walletId, BigDecimal.valueOf(1000)));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/get/{id}", 1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        Mockito.verify(walletService).getById(1L);
//    }
//
//    @Test
//    public void getNonExistingWallet() throws Exception{
//        Mockito.when(walletService.getById(222222L)).thenThrow(ResourceNotFoundException.class);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/wallet/get/{id}", 222222L))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//
//        Mockito.verify(walletService).getById(222222L);
//    }
//
//
//}
