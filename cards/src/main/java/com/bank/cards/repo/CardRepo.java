package com.bank.cards.repo;

import com.bank.cards.entities.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CardRepo extends CrudRepository<Card, Long> {
    Card findByCardNumber(String cardNumber);
    ArrayList<Card> findByAccountID(long accountID);
}
