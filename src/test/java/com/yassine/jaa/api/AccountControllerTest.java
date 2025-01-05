package com.yassine.jaa.api;


import com.yassine.jaa.api.dto.AccountDtoCreation;
import com.yassine.jaa.dto.AccountDto;
import com.yassine.jaa.dto.TransactionDto;
import com.yassine.jaa.model.TransactionTypes;
import com.yassine.jaa.service.AccountService;
import com.yassine.jaa.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateAccount() {
        Long accountId = 1L;
        Double initialBalance = 200.0;
        AccountDto accountDto = new AccountDto(accountId,  initialBalance);


        when(accountService.createAccount(initialBalance)).thenReturn(accountDto);


        AccountDto result = accountController.createAccount(new AccountDtoCreation(initialBalance));


        assertEquals(initialBalance, result.balance());
        verify(accountService, times(1)).createAccount(initialBalance);
    }



    @Test
    void testGetAccount() {
        Long accountId = 1L;
        AccountDto accountDto = new AccountDto(accountId,  0.0);


        when(accountService.getAccount(accountId)).thenReturn(accountDto);


        AccountDto result = accountController.getAccount(accountId);


        assertEquals(accountId, result.accountId());
        verify(accountService, times(1)).getAccount(accountId);
    }

    @Test
    void testGetAllAccounts() {
        AccountDto account1 = new AccountDto(1L, 100.0);
        AccountDto account2 = new AccountDto(2L, 200.0);
        List<AccountDto> accounts = Arrays.asList(account1, account2);


        when(accountService.getAllAccounts()).thenReturn(accounts);


        List<AccountDto> result = accountController.getAllAccounts();


        assertEquals(2, result.size());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void testGetTransactions() {
        Long accountId = 1L;
        List<TransactionDto> transactions = Arrays.asList(
                new TransactionDto(TransactionTypes.DEPOSIT.toString(), 100.0, 0.0, LocalDateTime.of(2024, 12,16, 0,0,0)),
                new TransactionDto(TransactionTypes.DEPOSIT.toString(), 50.0, 100.0, LocalDateTime.of(2024, 12,29, 0,0,0)));


        when(transactionService.findAllTransactions(accountId)).thenReturn(transactions);


        List<TransactionDto> result = accountController.getTransactions(accountId);


        assertEquals(2, result.size());
        assertEquals(50.0, result.get(1).amount());
        verify(transactionService, times(1)).findAllTransactions(accountId);
    }
}
