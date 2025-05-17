package com.bank.cards.controllers;

import com.bank.cards.dto.Response;
import com.bank.cards.entities.Card;
import com.bank.cards.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/cards")
public class CardsController {
    final Logger logger = LoggerFactory.getLogger(CardsController.class);
    final CardService cardService;

    public CardsController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Response> createCard(@RequestBody Card card) {
        logger.info("Create card");
        Response response = cardService.createCard(card);
        if(response != null) {
           logger.info("Create card successful");
           return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> getCardById(@PathVariable  Long id) {
        logger.info("Get card by id");
        Response response = cardService.getCardByAccountID(id);
        if(response != null) {
            logger.info("Get card by id successful");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
    }

    @PutMapping
    public ResponseEntity<Card> updateCard(@RequestBody Card card) {
        logger.info("Update card");
        Card updatedCard = cardService.updateCard(card);
        if(updatedCard != null) {
            logger.info("Update card successful");
            return new ResponseEntity<>(updatedCard, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
