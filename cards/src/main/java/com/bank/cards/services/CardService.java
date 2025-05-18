package com.bank.cards.services;

import com.bank.cards.configs.AccountsConfigs;
import com.bank.cards.dto.Account;
import com.bank.cards.dto.AccountDto;
import com.bank.cards.dto.CustomerInfoDto;
import com.bank.cards.dto.Response;
import com.bank.cards.entities.Card;
import com.bank.cards.repo.CardRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    final Logger logger = LoggerFactory.getLogger(CardService.class);
    final CardRepo cardRepo;
    final AccountsConfigs configs;
    final ObjectMapper mapper;
    public CardService(CardRepo cardRepo, AccountsConfigs configs, ObjectMapper mapper) {
        this.cardRepo = cardRepo;
        this.configs = configs;
        this.mapper = mapper;
    }

    public Response createCard(Card card) {
        try {
            // Check if the Account has the more one card and card type does not exist
            if(numberOfCards(card) < 2 && !cardTypeExists(card)) {
                logger.info("Creating new card");
                Response response = mapCardToResponse(card);
                logger.info("Successfully created card: {}", response.accounts);
                return response;
            }
            return null;
        }catch (Exception e) {
            logger.error("Error while creating a card: {}", e.getMessage());
        }
        return null;
    }

    public Response getCardByAccountID(Long id) {
       try {
           logger.info("Getting card by account ID: {}", id);
           Card card = cardRepo.findById(id).orElse(null);
           if(card != null){
               Response response = mapCardToResponse(card);
               logger.info("Successfully get card by account ID: {}", response.accounts);
               return response;
           }
       }catch (Exception e) {
           logger.error("Error while getting card by account ID: {}", e.getMessage());
       }
       return null;
    }

    public Card updateCard(Card card) {
        try {
            logger.info("Updating card: {}", card.getAliasName());
            Card card1 = cardRepo.findById(card.getId()).orElse(null);
            if(card1 != null){
                card1.setAliasName(card.getAliasName());
                return cardRepo.save(card1);
            }
            return null;
        }catch (Exception e){
            logger.error("Error while updating card: {}", e.getMessage());
        }
        return null;
    }
    public Response mapCardToResponse(Card card) {
        try {
            String resp = restClient().get().uri("/account-id/" + card.getAccountID()).retrieve().body(String.class);
            CustomerInfoDto customerInfoDto = mapper.readValue(resp, CustomerInfoDto.class);
            if (customerInfoDto.accounts().getFirst().accountNumber() != null && !customerInfoDto.accounts().getFirst().accountNumber().isEmpty()) {
                cardRepo.save(card);
                Response response = new Response();
                response.customer = customerInfoDto.customer();
                logger.info("Customer response: {}", response.customer);
                ArrayList<Account> accounts = new ArrayList<>();
                logger.info("Accounts response size: {}", customerInfoDto.accounts().size());
                for (AccountDto accountDto : customerInfoDto.accounts()) {
                    Account account = new Account();
                    account.accountType = accountDto.accountType();
                    account.accountNumber = accountDto.accountNumber();
                    account.iban = accountDto.iban();
                    account.bicSwift = accountDto.bicSwift();
                    account.id = accountDto.id();
                    account.cards = cardRepo.findByAccountID(accountDto.id());
                    logger.info("Account cards: {} ", account.cards.size());
                    accounts.add(account);
                }
                response.accounts = accounts;
                return response;
            }
        }catch (Exception e) {
            logger.error("Error while mapping a card: {}", e.getMessage());
            return null;
        }
        return null;
    }

    int numberOfCards(Card card) {
        List<Card> cards = cardRepo.findByAccountID(card.getAccountID());
        return cards.size();
    }

    boolean cardTypeExists(Card card) {
        List<Card> cards = cardRepo.findByAccountID(card.getAccountID());
        for (Card ca : cards) {
            if(ca.getCardType() == card.getCardType()) {
                return true;
            }
        }
        return false;
    }
    RestClient restClient(){
        return RestClient.builder().baseUrl(configs.baseUrl()).build();
    }
}
