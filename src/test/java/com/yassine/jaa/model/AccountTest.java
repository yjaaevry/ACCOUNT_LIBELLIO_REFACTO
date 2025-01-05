package com.yassine.jaa.model;

import com.yassine.jaa.exception.AccountGenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L);
    }

    @Test
    void testDeposit_validAmount() {

        Double initialBalance = account.getBalance();
        Double depositAmount = 100.0;


        account.deposit(depositAmount);


        assertEquals(initialBalance + depositAmount, account.getBalance(), "The balance should be updated after deposit");
    }

    @Test
    void testDeposit_negativeAmount() {

        AccountGenericException exception = assertThrows(AccountGenericException.class, () -> account.deposit(-50.0));
        assertEquals("Deposit amount must be positive.", exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN.value(), exception.getHttpStatusCode());
    }

    @Test
    void testWithdraw_validAmount() {

        account.setBalance(200.0);
        Double withdrawalAmount = 50.0;


        account.withdraw(withdrawalAmount);


        assertEquals(150.0, account.getBalance(), "The balance should be reduced after withdrawal");
    }

    @Test
    void testWithdraw_insufficientBalance() {

        account.setBalance(100.0);
        Double withdrawalAmount = 150.0;


        AccountGenericException exception = assertThrows(AccountGenericException.class, () -> account.withdraw(withdrawalAmount));
        assertEquals("Insufficient balance.", exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN.value(), exception.getHttpStatusCode());
    }

    @Test
    void testWithdraw_negativeAmount() {

        AccountGenericException exception = assertThrows(AccountGenericException.class, () -> account.withdraw(-50.0));
        assertEquals("Withdrawal amount must be positive.", exception.getMessage());
        assertEquals(HttpStatus.FORBIDDEN.value(), exception.getHttpStatusCode());
    }
}