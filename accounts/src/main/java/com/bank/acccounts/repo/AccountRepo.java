package com.bank.acccounts.repo;

import com.bank.acccounts.entities.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepo extends CrudRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);
}
