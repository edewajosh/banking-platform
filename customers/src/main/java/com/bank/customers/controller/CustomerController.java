package com.bank.customers.controller;

import com.bank.customers.entities.Customer;
import com.bank.customers.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CRUD
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        logger.info("Creating customer: {} {}", customer.getFirstName(), customer.getLastName());
        Customer cust = customerService.createCustomer(customer);
        if (cust != null) {
            return new ResponseEntity<>(cust, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        logger.info("Updating customer: {} {}", customer.getFirstName(), customer.getLastName());
        Customer customer1 = customerService.findCustomerById(id);
        if (customer1 != null) {
            customer1.setFirstName(customer.getFirstName());
            customer1.setLastName(customer.getLastName());
            customerService.createCustomer(customer1);
            logger.info("Updated customer: {} {}", customer1.getFirstName(), customer1.getLastName());
            return new ResponseEntity<>(customer1, HttpStatus.OK);
        }
        return new ResponseEntity<>(customer, HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Long id) {
        logger.info("Find customer by id: {}", id);
        Customer cust = customerService.findCustomerById(id);
        logger.info("Found customer: {}", cust);
        return new ResponseEntity<>(cust, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers() {
        logger.info("Find all customers");
        List<Customer> customers = customerService.findAllCustomers();
        if (customers != null) {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        logger.info("Deleting customer: {}", id);
        boolean deleted = customerService.deleteCustomer(id);
        if(deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
