package com.bank.customers.repo;

import com.bank.customers.entities.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {
}
