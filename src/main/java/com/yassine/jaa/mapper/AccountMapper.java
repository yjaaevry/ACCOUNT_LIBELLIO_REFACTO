package com.yassine.jaa.mapper;

import com.yassine.jaa.dto.AccountDto;
import com.yassine.jaa.exception.AccountGenericException;
import com.yassine.jaa.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    private static final Logger log = LoggerFactory.getLogger(AccountMapper.class);

    public AccountDto accountEntityToDto(Account account) {
        try {
            return new AccountDto(account.getAccountId(), account.getBalance());
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new AccountGenericException("Unable to retrieve Account information %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
