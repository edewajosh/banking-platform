package com.bank.cards.services;

import com.bank.cards.configs.AccountsConfigs;
import com.bank.cards.dto.*;
import com.bank.cards.entities.Card;
import com.bank.cards.repo.CardRepo;
import com.bank.cards.utils.ResponseHeader;
import com.bank.cards.utils.SuccessFailureEnums;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            int numberOfCards = numberOfCards(card);
            logger.info("Account with ID: {} has {}", card.getAccountID(), numberOfCards);
            Response response = new Response();
            if(numberOfCards <= 2) {
                boolean exists = cardTypeExists(card);
                logger.info("Card type exists: {} -> {}", card.getCardType(), exists);
                if(!exists) {
                    logger.info("Creating new card");
                    response = mapCardToResponse(card, "true", true);
                    if(response.primaryData != null) {
                        logger.info("Successfully created card: {}", response.primaryData.accounts);
                        ResponseHeader header = new ResponseHeader(SuccessFailureEnums.SUCCESS_CODE,
                                SuccessFailureEnums.SUCCESS_STATUS_MESSAGE, "201", "Card created successfully");
                        logger.info("Success response header: {}", header);
                        response.responseHeader = header;
                    }else {
                        ResponseHeader header = new ResponseHeader(SuccessFailureEnums.SUCCESS_CODE,
                                SuccessFailureEnums.SUCCESS_STATUS_MESSAGE, "400", "Error creating card");
                        logger.info("Failed response header: {}", header);
                        response.responseHeader = header;
                    }
                }else{
                    ResponseHeader header = new ResponseHeader(SuccessFailureEnums.FAILURE_CODE,
                            SuccessFailureEnums.FAILURE_STATUS_MESSAGE, "400", "Card type exists for the account");
                    response.primaryData = null;
                    response.responseHeader = header;
                }
            }else{
                ResponseHeader header = new ResponseHeader(SuccessFailureEnums.FAILURE_CODE,
                        SuccessFailureEnums.FAILURE_STATUS_MESSAGE, "400", "Account has 2 already created cards");
                response.primaryData = null;
                logger.info("Account has 2 cards already");
                response.responseHeader = header;
            }
            return response;
        }catch (Exception e) {
            logger.error("Error while creating a card: {}", e.fillInStackTrace());
        }
        return null;
    }

    public Response getCardByAccountID(Long id, String unmask) {
       try {
           logger.info("Getting card by account ID: {}", id);
           Card card = cardRepo.findById(id).orElse(null);
           if(card != null){
               Response response = mapCardToResponse(card, unmask, false);
               logger.info("Successfully get card by account ID: {}", response.primaryData.accounts);
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
    public Response mapCardToResponse(Card card, String unmask, boolean create) {
        try {
            String resp = restClient().get().uri("/account-id/" + card.getAccountID()).retrieve().body(String.class);
            CustomerInfoDto customerInfoDto = mapper.readValue(resp, CustomerInfoDto.class);
            logger.info("Customer info: {}", customerInfoDto);
            Response response = new Response();
            if(customerInfoDto.accounts().getFirst().accountNumber() != null && !customerInfoDto.accounts().getFirst().accountNumber().isEmpty()) {
                if(create) {
                    cardRepo.save(card);
                }
                PrimaryData data = new PrimaryData();
                data.customer =customerInfoDto.customer();
                logger.info("Customer response: {}", data.customer);
                ArrayList<Account> accounts = new ArrayList<>();
                logger.info("Accounts response size: {}", customerInfoDto.accounts().size());
                for (AccountDto accountDto : customerInfoDto.accounts()) {
                    Account account = new Account();
                    account.accountType = accountDto.accountType();
                    account.accountNumber = accountDto.accountNumber();
                    account.iban = accountDto.iban();
                    account.bicSwift = accountDto.bicSwift();
                    account.id = accountDto.id();
                    account.cards = cardRepo.findByAccountID(accountDto.id()).stream().peek(card1 -> {
                        if(Objects.equals(unmask, "true")) {
                            card1.setCvv("XXX");
                            card1.setPan("DTBXXX");
                        }
                    }).toList();
                    logger.info("Account cards: {} ", account.cards.size());
                    accounts.add(account);
                }
                data.accounts = accounts;
                response.primaryData = data;
            }else {
                response.responseHeader = new ResponseHeader(SuccessFailureEnums.FAILURE_CODE, SuccessFailureEnums.FAILURE_STATUS_MESSAGE,
                        "400", "Account does not exist");
                response.primaryData = null;
            }
            return response;
        }catch (Exception e) {
            logger.error("Error while mapping a card: {}", e.getMessage());
            return null;
        }
    }

    int numberOfCards(Card card) {
        List<Card> cards = cardRepo.findByAccountID(card.getAccountID());
        logger.info("Number of cards: {}", cards.size());
        return cards.size();
    }

    boolean cardTypeExists(Card card) {
        List<Card> cards = cardRepo.findByAccountID(card.getAccountID());
        for (Card ca : cards) {
            if(Objects.equals(ca.getCardType(), card.getCardType())) {
                logger.info("Account with card type: {} found", ca.getCardType());
                return true;
            }
        }
        return false;
    }
    RestClient restClient(){
        return RestClient.builder().baseUrl(configs.baseUrl()).build();
    }
}
