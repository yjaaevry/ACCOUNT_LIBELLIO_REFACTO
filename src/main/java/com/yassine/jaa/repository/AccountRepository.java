package com.yassine.jaa.repository;

import com.yassine.jaa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
