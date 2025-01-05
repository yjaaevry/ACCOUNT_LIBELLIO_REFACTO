package com.yassine.jaa.service;

import com.yassine.jaa.dto.AccountDto;
import com.yassine.jaa.exception.AccountGenericException;
import com.yassine.jaa.mapper.AccountMapper;
import com.yassine.jaa.model.Account;
import com.yassine.jaa.model.Transaction;
import com.yassine.jaa.model.TransactionTypes;
import com.yassine.jaa.repository.AccountRepository;
import com.yassine.jaa.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public AccountDto createAccount(Double initialBalance) {
        Account account = new Account();
        account.setBalance(initialBalance);
        auditTransaction(TransactionTypes.DEPOSIT, initialBalance, account);

        return accountMapper.accountEntityToDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto performTransaction(Long accountId, Double amount, TransactionTypes transactionTypes) {
        return switch (transactionTypes) {
            case WITHDRAW -> this.withdraw(accountId, amount);
            case DEPOSIT -> this.deposit(accountId, amount);
        };

    }

    public AccountDto deposit(Long accountId, Double amount) {

        Account account = findAccountById(accountId);
        account.deposit(amount);

        auditTransaction(TransactionTypes.DEPOSIT, amount, account);

        return accountMapper.accountEntityToDto(accountRepository.save(account));
    }

    public AccountDto withdraw(Long accountId, Double amount) {
        Account account = findAccountById(accountId);
        account.withdraw(amount);

        auditTransaction(TransactionTypes.WITHDRAW, amount, account);

        return accountMapper.accountEntityToDto(accountRepository.save(account));
    }

    public AccountDto getAccount(Long accountId) {
        return accountMapper.accountEntityToDto(findAccountById(accountId));
    }

    public List<AccountDto> getAllAccounts() {
        return this.accountRepository.findAll().stream().map(i -> accountMapper.accountEntityToDto(i)).toList();
    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountGenericException("Unable to find Account %s".formatted(accountId), HttpStatus.NOT_FOUND));
    }

    private void auditTransaction(TransactionTypes initialDeposit, Double initialBalance, Account account) {
        Transaction t = new Transaction();

        t.setType(initialDeposit);
        t.setAmount(initialBalance);
        t.setDate(LocalDateTime.now());
        t.setBalance(account.getBalance());
        t.setAccount(account);

        transactionRepository.save(t);
        log.info("Transaction performed : %s ".formatted(t.toString()));
    }


}
