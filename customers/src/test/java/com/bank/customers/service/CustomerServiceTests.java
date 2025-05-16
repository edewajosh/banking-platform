package com.bank.customers.service;

import com.bank.customers.entities.Customer;
import com.bank.customers.repo.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceTests {
    @Mock
    CustomerRepo customerRepo;

    @Test
    void createCustomer() {
        Customer mockResult = new Customer("Ronald", "Jeremy", "") ;
        when(customerRepo.save(any(Customer.class))).thenReturn(mockResult);
        assertNotNull(mockResult);
        assertEquals("Ronald", mockResult.getFirstName());
    }
}
