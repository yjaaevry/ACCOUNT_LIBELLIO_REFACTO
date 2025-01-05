package com.yassine.jaa.repository;


import com.yassine.jaa.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId ORDER BY t.date ASC")
    List<Transaction> findAllByAccountId(@Param(value="accountId") Long accountId);
}
