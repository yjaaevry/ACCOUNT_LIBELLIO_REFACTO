package com.yassine.jaa.model;

import com.yassine.jaa.exception.AccountGenericException;
import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.List;


@ToString(exclude = {"transactions"})
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;

    @Column(name = "balance")
    Double balance = 0.0;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions = new ArrayList<>();


    public void deposit(Double amount ) {

        if (amount <= 0) {

            throw new AccountGenericException("Deposit amount must be positive.", HttpStatus.FORBIDDEN);
        }

        this.balance += amount;
    }

    public void withdraw(Double amount) {
        if (amount <= 0){
            throw new AccountGenericException("Withdrawal amount must be positive.", HttpStatus.FORBIDDEN);
        }


        if (amount > this.balance) {
            throw new AccountGenericException("Insufficient balance.", HttpStatus.FORBIDDEN);
        }
        this.balance -= amount;

    }

    public Long getAccountId() {
        return accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    public Account() {
    }

}
