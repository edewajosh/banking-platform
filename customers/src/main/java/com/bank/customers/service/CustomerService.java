package com.bank.customers.service;

import com.bank.customers.entities.Customer;
import com.bank.customers.repo.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    final CustomerRepo customerRepo;

    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer createCustomer(Customer customer) {
        try {
            logger.info("Creating new customer: {}  {} ", customer.getFirstName(), customer.getLastName());
            return customerRepo.save(customer);
        }catch (Exception e) {
            logger.error("Error occurred while creating {}", e.getMessage());
            return null;
        }
    }

    public Customer findCustomerById(Long id) {
        // TODO Add logic to fetch accounts for the customer
        logger.info("Find customer by id: {}", id);
        try{
            Optional<Customer> customer = customerRepo.findById(id);
            logger.info("Customer found: {}", customer);
            return customer.orElse(null);
        }catch (Exception e) {
            logger.error("Error occurred while finding {}", e.getMessage());
            return null;
        }
    }
    public List<Customer> findAllCustomers() {
        // TODO Add logic to fetch accounts associated for each customer
        logger.info("Find all customers");
        try {
            List<Customer> customers = (List<Customer>) customerRepo.findAll();
            logger.info("Customers size: {}", customers.size());
            return customers;
        } catch (Exception e) {
            logger.error("Error occurred while fetching customer {}", e.getMessage());
            return null;
        }
    }
    public void deleteCustomer(Long id) {
        logger.info("Delete customer by id: {}", id);
        try {
            customerRepo.deleteById(id);
        }catch (Exception e) {
            logger.error("Error occurred while deleting {}", e.getMessage());
        }
    }
}
