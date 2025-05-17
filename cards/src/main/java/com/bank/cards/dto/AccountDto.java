package com.bank.cards.dto;

public record AccountDto(Long id, String accountNumber, String iban, String bicSwift, String accountType) {
}
