package com.yassine.jaa.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.yassine.jaa.dto.AccountDto;
import com.yassine.jaa.exception.AccountGenericException;
import com.yassine.jaa.mapper.AccountMapper;
import com.yassine.jaa.model.Account;
import com.yassine.jaa.model.Transaction;
import com.yassine.jaa.model.TransactionTypes;
import com.yassine.jaa.repository.AccountRepository;
import com.yassine.jaa.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.List;
import java.util.Optional;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountMapper accountMapper;

    private AccountService accountService;

    private Account account;
    private AccountDto accountDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository, transactionRepository, accountMapper);


        account = new Account();
        account.setBalance(1000.0);
        account.setAccountId(1L);

        accountDto = new AccountDto(1L, 1000.0);
    }

    @Test
    void testCreateAccount() {

        Double initialBalance = 1000.0;
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.accountEntityToDto(account)).thenReturn(accountDto);


        AccountDto result = accountService.createAccount(initialBalance);


        assertNotNull(result);
        assertEquals(accountDto.accountId(), result.accountId());
        assertEquals(accountDto.balance(), result.balance());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void testPerformTransaction_Deposit() {

        Double depositAmount = 500.0;
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.accountEntityToDto(account)).thenReturn(accountDto);


        AccountDto result = accountService.performTransaction(1L, depositAmount, TransactionTypes.DEPOSIT);

        assertNotNull(result);
        assertEquals(accountDto.balance(), result.balance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testPerformTransaction_Withdraw() {

        Double withdrawAmount = 200.0;
        account.setBalance(1000.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountMapper.accountEntityToDto(account)).thenReturn(accountDto);


        AccountDto result = accountService.performTransaction(1L, withdrawAmount, TransactionTypes.WITHDRAW);


        assertNotNull(result);
        assertEquals(accountDto.balance(), result.balance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testGetAccount() {

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.accountEntityToDto(account)).thenReturn(accountDto);

        AccountDto result = accountService.getAccount(1L);

        assertNotNull(result);
        assertEquals(accountDto.accountId(), result.accountId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllAccounts() {

        when(accountRepository.findAll()).thenReturn(List.of(account));
        when(accountMapper.accountEntityToDto(account)).thenReturn(accountDto);


        List<AccountDto> result = accountService.getAllAccounts();


        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void testFindAccountById_ThrowsException_WhenAccountNotFound() {

        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());


        AccountGenericException exception = assertThrows(AccountGenericException.class, () -> accountService.getAccount(1L));
        assertEquals("Unable to find Account 1", exception.getMessage());
    }
}
