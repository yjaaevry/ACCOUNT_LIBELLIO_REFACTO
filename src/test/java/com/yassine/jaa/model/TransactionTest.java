package com.yassine.jaa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class TransactionTest {

    private Transaction transaction;
    private Account account;

    @BeforeEach
    void setUp() {
        account = mock(Account.class);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setType(TransactionTypes.DEPOSIT);
        transaction.setAmount(100.0);
        transaction.setDate(LocalDateTime.of(2024, 12, 17, 10, 0, 0));
        transaction.setBalance(1000.0);
        transaction.setAccount(account);
    }

    @Test
    void testGetId() {
        assertEquals(1L, transaction.getId(), "The id should be 1");
    }

    @Test
    void testGetType() {
        assertEquals(TransactionTypes.DEPOSIT, transaction.getType(), "The transaction type should be DEPOSIT");
    }

    @Test
    void testGetAmount() {
        assertEquals(100.0, transaction.getAmount(), "The amount should be 100.0");
    }

    @Test
    void testGetDate() {
        assertNotNull(transaction.getDate(), "The transaction date should not be null");
    }

    @Test
    void testGetBalance() {
        assertEquals(1000.0, transaction.getBalance(), "The balance should be 1000.0");
    }

    @Test
    void testGetAccount() {
        assertNotNull(transaction.getAccount(), "The account should not be null");
    }

    @Test
    void testSetAmount() {

        Double newAmount = 200.0;


        transaction.setAmount(newAmount);


        assertEquals(newAmount, transaction.getAmount(), "The amount should be updated to 200.0");
    }

    @Test
    void testSetType() {

        TransactionTypes newType = TransactionTypes.WITHDRAW;


        transaction.setType(newType);


        assertEquals(newType, transaction.getType(), "The type should be updated to WITHDRAW");
    }

    @Test
    void testSetBalance() {

        Double newBalance = 1500.0;


        transaction.setBalance(newBalance);


        assertEquals(newBalance, transaction.getBalance(), "The balance should be updated to 1500.0");
    }

    @Test
    void testSetAccount() {

        Account newAccount = mock(Account.class);


        transaction.setAccount(newAccount);


        assertEquals(newAccount, transaction.getAccount(), "The account should be updated");
    }

    @Test
    void testToString() {
        String expectedString = "Transaction{id=1, type=DEPOSIT, amount=100.0, date=" + transaction.getDate() +
                ", balance=1000.0, account=" + account + "}";
        assertEquals(expectedString, transaction.toString(), "The toString method should return the correct string");
    }
}