package com.bank.acccounts.services;

import com.bank.acccounts.configs.CustomerConfigs;
import com.bank.acccounts.dto.CustomerDto;
import com.bank.acccounts.dto.CustomerInfoDto;
import com.bank.acccounts.dto.response.Customer;
import com.bank.acccounts.dto.response.Response;
import com.bank.acccounts.entities.Account;
import com.bank.acccounts.repo.AccountRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;

@Service
public class AccountService {
    final Logger logger = LoggerFactory.getLogger(AccountService.class);
    final AccountRepo accountRepo;
    final CustomerConfigs configs;
    final ObjectMapper objectMapper;
    public AccountService(AccountRepo accountRepo, CustomerConfigs configs, ObjectMapper objectMapper) {
        this.accountRepo = accountRepo;
        this.configs = configs;
        this.objectMapper = objectMapper;
    }

    public CustomerInfoDto createAccount(Account account){
        try {
            logger.info("Creating account: {}", account.getAccountNumber());
            String response = restClient().get().uri("/" + account.getCustomerId()).retrieve().body(String.class);
            logger.info("Raw response customer service : {}", response);
            Response res = objectMapper.readValue(response, Response.class);
            if (res.primaryData != null) {
                account.setCustomerId(res.primaryData.customer.id());
                Account account1 = accountRepo.save(account);
                logger.info("Saved account : {}", account1);
                Customer customer = res.primaryData.customer;

                CustomerDto customerDto = new CustomerDto(customer.id(), customer.firstName(), customer.lastName(), customer.otherName());
                CustomerInfoDto customerInfoDto = new CustomerInfoDto(customerDto, new ArrayList<>(){{add(account);}});
                logger.info("Mapped account to customer info : {}", customerInfoDto);
                return customerInfoDto;
            }
            logger.info("Customer with ID does not exist : {}", account);
            return null;

        }catch (Exception e) {
            logger.error("Error occurred while creating account: {}", e.getMessage());
            return null;
        }
    }
    public CustomerInfoDto getAccount(String accountNumber){
        try {
          logger.info("Getting account number info : {}", accountNumber);
          Account account = accountRepo.findByAccountNumber(accountNumber);
          if (account != null) {
              String response = restClient().get().uri("/"+ account.getCustomerId()).retrieve().body(String.class);
              logger.info("A/C-Raw response customer service : {}", response);
              Response res = objectMapper.readValue(response, Response.class);
              logger.info("A/C-Mapped account to customer info : {}", res.primaryData.customer);
              Customer customer = res.primaryData.customer;
              CustomerDto customerDto = new CustomerDto(customer.id(), customer.firstName(), customer.lastName(), customer.otherName());
              CustomerInfoDto customerInfoDto = new CustomerInfoDto(customerDto, new ArrayList<>(){{add(account);}});
              logger.info("A/C-Mapped account info to customer info : {}", customerInfoDto);
              return customerInfoDto;
          }else {
              logger.info("Customer with that accountNumber [{}] is missing", accountNumber);
          }
        }catch (Exception e){
            logger.error("Error occurred while getting account : {}", e.getMessage());
            return null;
        }
        return null;
    }

    public CustomerInfoDto getAccountById(Long accountID) {
        try {
            logger.info("Getting account info by : {}", accountID);
            Account account = accountRepo.findById(accountID).orElse(null);
            if (account != null) {
                String response = restClient().get().uri("/"+ account.getCustomerId()).retrieve().body(String.class);
                logger.info("Raw response customer service by ID : {}", response);
                Response res = objectMapper.readValue(response, Response.class);
                logger.info("Mapped response account to customer info : {}", res.primaryData.customer);
                Customer customer = res.primaryData.customer;
                CustomerDto customerDto = new CustomerDto(customer.id(), customer.firstName(), customer.lastName(), customer.otherName());
                CustomerInfoDto customerInfoDto = new CustomerInfoDto(customerDto, new ArrayList<>(){{add(account);}});
                logger.info("Mapped account info to customer info : {}", customerInfoDto);
                return customerInfoDto;
            }else {
                logger.info("Customer with that account id [{}] is missing", accountID);
            }
        }catch (Exception e){
            logger.error("Error occurred while getting account by ID : {}", e.getMessage());
            return null;
        }
        return null;
    }
    RestClient restClient(){
        return RestClient.builder().baseUrl(configs.baseUrl()).build();
    }
}
