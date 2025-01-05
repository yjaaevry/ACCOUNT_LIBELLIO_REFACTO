package com.yassine.jaa.mapper;

import com.yassine.jaa.dto.TransactionDto;
import com.yassine.jaa.exception.AccountGenericException;
import com.yassine.jaa.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    private static final Logger log = LoggerFactory.getLogger(TransactionMapper.class);

    public TransactionDto entityToDto(Transaction t) {
        try {
            return new TransactionDto(t.getType().toString(), t.getAmount(), t.getBalance(), t.getDate());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AccountGenericException("Unable to retrieve Account information %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
