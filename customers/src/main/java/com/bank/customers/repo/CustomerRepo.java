package com.bank.customers.repo;

import com.bank.customers.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
}
