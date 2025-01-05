package com.yassine.jaa.api;

import com.yassine.jaa.api.dto.AccountDtoCreation;
import com.yassine.jaa.dto.AccountDto;

import com.yassine.jaa.dto.TransactionDto;
import com.yassine.jaa.model.TransactionTypes;
import com.yassine.jaa.service.AccountService;
import com.yassine.jaa.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    AccountService accountService;
    TransactionService transactionService;

    @PostMapping("")
    @Operation(summary = "create account", description = "create account")
    public AccountDto createAccount(@RequestBody AccountDtoCreation dto) {
        log.info("Request to create account with initial amount %s ".formatted(dto.initialBalance()));

        return accountService.createAccount(dto.initialBalance());
    }

    @GetMapping("")
    @Operation(summary = "retrieve all accounts", description = "retrieve all accounts")
    public List<AccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "retrieve single account", description = "retrieve single account")
    public AccountDto getAccount(@PathVariable(name = "accountId") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PostMapping("/{accountId}")
    @Operation(summary = "perform widthdraw or deposit in account", description = "perform widthdraw or deposit in account")
    public AccountDto performTransaction(@PathVariable(name = "accountId", required = true) Long accountId,
                                  @RequestParam(name = "transactionType", required = true) TransactionTypes transactionTypes,
                                  @RequestParam(name = "amount", required = true) Double amount
    ) {
        log.info("Request to %s %s from %s ".formatted(transactionTypes.toString(), amount, accountId));
        return accountService.performTransaction(accountId, amount, transactionTypes );

    }


    @GetMapping("/{accountId}/transactions")
    @Operation(summary = "view audit transactions", description = "view audit transactions")
    public List<TransactionDto> getTransactions(@PathVariable(name = "accountId", required = true) Long accountId) {

        return transactionService.findAllTransactions(accountId);
    }
}
