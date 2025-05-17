package com.bank.cards.dto;

import java.util.ArrayList;

public class Response {
    public CustomerDto customer;
    public ArrayList<Account> accounts;

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
}
