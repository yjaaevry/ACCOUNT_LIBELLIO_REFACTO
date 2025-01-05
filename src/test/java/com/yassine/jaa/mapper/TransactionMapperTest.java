package com.yassine.jaa.mapper;

import com.yassine.jaa.dto.TransactionDto;
import com.yassine.jaa.exception.AccountGenericException;
import com.yassine.jaa.model.Transaction;
import com.yassine.jaa.model.TransactionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionMapperTest {

    @InjectMocks
    private TransactionMapper transactionMapper;

    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEntityToDto_success() {

        String transactionType = "DEPOSIT";
        double amount = 100.0;
        double balance = 200.0;
        LocalDateTime date = LocalDateTime.of(2024, 12, 17, 10,0,0);

        when(transaction.getType()).thenReturn(TransactionTypes.DEPOSIT);
        when(transaction.getAmount()).thenReturn(amount);
        when(transaction.getBalance()).thenReturn(balance);
        when(transaction.getDate()).thenReturn(LocalDateTime.of(2024, 12, 17, 10,0,0));


        TransactionDto result = transactionMapper.entityToDto(transaction);


        assertNotNull(result, "The result should not be null");
        assertEquals(transactionType, result.type(), "The transaction type should match");
        assertEquals(amount, result.amount(), "The transaction amount should match");
        assertEquals(balance, result.balance(), "The transaction balance should match");
        assertEquals(date, result.date(), "The transaction date should match");
    }

    @Test
    void testEntityToDto_exception() {

        when(transaction.getType()).thenThrow(new RuntimeException("Database error"));


        AccountGenericException exception = assertThrows(AccountGenericException.class, () -> {
            transactionMapper.entityToDto(transaction);
        });

        assertEquals("Unable to retrieve Account information Database error", exception.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getHttpStatusCode(), "The status should be INTERNAL_SERVER_ERROR");
    }
}