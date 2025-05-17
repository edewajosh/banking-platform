package com.bank.cards.dto;
import java.util.ArrayList;

public record CustomerInfoDto(CustomerDto customer, ArrayList<AccountDto> accounts) {
}
