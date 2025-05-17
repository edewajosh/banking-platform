package com.bank.acccounts.dto;

import com.bank.acccounts.entities.Account;

import java.util.ArrayList;

public record CustomerInfoDto(CustomerDto customer, ArrayList<Account> accounts) {
}
