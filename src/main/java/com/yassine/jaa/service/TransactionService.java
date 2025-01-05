package com.yassine.jaa.service;

import com.yassine.jaa.dto.TransactionDto;
import com.yassine.jaa.mapper.TransactionMapper;
import com.yassine.jaa.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    public List<TransactionDto> findAllTransactions(Long accountId) {

        return transactionRepository.findAllByAccountId(accountId)
                .stream().map( i -> transactionMapper.entityToDto(i)).toList();
    }

}
