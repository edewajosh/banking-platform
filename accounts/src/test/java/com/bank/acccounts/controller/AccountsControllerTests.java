package com.bank.acccounts.controller;

import com.bank.acccounts.dto.CustomerDto;
import com.bank.acccounts.dto.CustomerInfoDto;
import com.bank.acccounts.entities.Account;
import com.bank.acccounts.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountsControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    AccountService accountService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createAccount_ShouldReturn() throws Exception {
        CustomerInfoDto result = getCustomerInfoDto();
        Account request = new Account("123345", "IBAN1234", "SWIFTKEN123", 1L, "DEBIT");
        when(accountService.createAccount(any(Account.class))).thenReturn(result);
        mockMvc.perform(post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
        verify(accountService).createAccount(any(Account.class));
    }

    @Test
    void getAccount_ShouldReturn() throws Exception {
        when(accountService.getAccountById(anyLong())).thenReturn(getCustomerInfoDto());
        mockMvc.perform(get("/api/v1/account/account-id/1")).andExpect(status().isOk());
        verify(accountService).getAccountById(anyLong());
    }

    @Test
    void invalidUriGetAccount_ShouldReturn() throws Exception {
        mockMvc.perform(get("/api/v1/account/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    void getAccountByAccountNumber_ShouldReturn() throws Exception {
        when(accountService.getAccount(anyString())).thenReturn(getCustomerInfoDto());
        mockMvc.perform(get("/api/v1/account/1")).andExpect(status().isOk());
        verify(accountService).getAccount(anyString());
    }


    Account getAccount(){
        return new Account("123345", "IBAN1234", "SWIFTKEN123", 1L, "DEBIT");
    }

    CustomerDto getCustomerDto(){
        return new CustomerDto(1L, "Jane", "Doe", "");
    }

    CustomerInfoDto getCustomerInfoDto(){
        return new CustomerInfoDto(getCustomerDto(), new ArrayList<>(){{add(getAccount());}});
    }
}
