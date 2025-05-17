package com.bank.cards.entities;

import jakarta.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String cardNumber;
    private String cardType;
    @Column(updatable = false, nullable = false)
    private String cvv;
    @Column(updatable = false, nullable = false)
    private String pan;
    private String aliasName;
    private String expiryDate;
    private Long accountID;

    public Card() {}
    public Card(String cardNumber, String cardType, String cvv, String pan,
                String aliasName, String expiryDate, Long accountID) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cvv = cvv;
        this.pan = pan;
        this.aliasName = aliasName;
        this.expiryDate = expiryDate;
        this.accountID = accountID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cvv='" + cvv + '\'' +
                ", pan='" + pan + '\'' +
                ", aliasName='" + aliasName + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
