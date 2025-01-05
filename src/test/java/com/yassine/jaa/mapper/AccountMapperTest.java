package com.yassine.jaa.mapper;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.yassine.jaa.dto.AccountDto;
import com.yassine.jaa.exception.AccountGenericException;
import com.yassine.jaa.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

class AccountMapperTest {

    @InjectMocks
    private AccountMapper accountMapper;

    @Mock
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAccountEntityToDto_success() {

        long accountId = 1L;
        double balance = 1000.0;
        when(account.getAccountId()).thenReturn(accountId);
        when(account.getBalance()).thenReturn(balance);

        AccountDto result = accountMapper.accountEntityToDto(account);

        assertNotNull(result, "The result should not be null");
        assertEquals(accountId, result.accountId(), "The accountId should match");
        assertEquals(balance, result.balance(), "The balance should match");
    }

    @Test
    void testAccountEntityToDto_exception() {

        when(account.getAccountId()).thenThrow(new RuntimeException("Database error"));

        AccountGenericException exception = assertThrows(AccountGenericException.class, () -> accountMapper.accountEntityToDto(account));

        assertEquals("Unable to retrieve Account information Database error", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode(), "The status should be INTERNAL_SERVER_ERROR");
    }
}