package com.bank.acccounts.controller;

import com.bank.acccounts.dto.CustomerInfoDto;
import com.bank.acccounts.entities.Account;
import com.bank.acccounts.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {
    final Logger logger = LoggerFactory.getLogger(AccountController.class);
    final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<CustomerInfoDto> createAccount(@RequestBody Account account) {
        logger.info("Creating account : {}", account);
        CustomerInfoDto resp = accountService.createAccount(account);
        if(resp != null){
            logger.info("Customer created");
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping(value = "/{accountNumber}")
    public ResponseEntity<CustomerInfoDto> getAccount(@PathVariable String accountNumber) {
        logger.info("Get account : {}", accountNumber);
        CustomerInfoDto customerInfoDto = accountService.getAccount(accountNumber);
        if(customerInfoDto != null){
            logger.info("Customer found");
            return new ResponseEntity<>(customerInfoDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/account-id/{id}")
    public ResponseEntity<CustomerInfoDto> getAccountById(@PathVariable Long id) {
        logger.info("Get account id : {}", id);
        CustomerInfoDto customerInfoDto = accountService.getAccountById(id);
        if(customerInfoDto != null){
            logger.info("Customer with ID found");
            return new ResponseEntity<>(customerInfoDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
