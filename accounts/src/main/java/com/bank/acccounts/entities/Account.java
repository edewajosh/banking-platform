package com.bank.acccounts.entities;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String accountNumber;
    private String iban;
    private String bicSwift;
    private Long customerId;
    private String accountType;

    public Account() {}
    public Account(String accountNumber, String iban, String bicSwift, Long customerId, String accountType) {
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.bicSwift = bicSwift;
        this.customerId = customerId;
        this.accountType = accountType;
    }
    public Account(Long id, String accountNumber, String iban, String bicSwift, Long customerId, String accountType){
      this.id = id;
      this.accountNumber = accountNumber;
      this.iban = iban;
      this.bicSwift = bicSwift;
      this.customerId = customerId;
      this.accountType = accountType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBicSwift() {
        return bicSwift;
    }

    public void setBicSwift(String bicSwift) {
        this.bicSwift = bicSwift;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", iban='" + iban + '\'' +
                ", bicSwift='" + bicSwift + '\'' +
                ", customerId=" + customerId +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
