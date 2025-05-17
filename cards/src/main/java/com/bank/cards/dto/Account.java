package com.bank.cards.dto;

import com.bank.cards.entities.Card;

import java.util.List;

public class Account{
    public Long id;
    public String accountNumber;
    public String iban;
    public String bicSwift;
    public String accountType;
    public List<Card> cards;
}
