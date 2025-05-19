package com.bank.customers.controller;

import com.bank.customers.dto.PrimaryData;
import com.bank.customers.dto.Response;
import com.bank.customers.entities.Customer;
import com.bank.customers.service.CustomerService;
import com.bank.customers.utils.ResponseHeader;
import com.bank.customers.utils.SuccessFailureEnums;
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
    public ResponseEntity<Response> createCustomer(@RequestBody Customer customer) {
        logger.info("Creating customer: {} {}", customer.getFirstName(), customer.getLastName());
        Response response = customerService.createCustomer(customer);
        if (response.primaryData != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Response> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        logger.info("Updating customer: {} {}", customer.getFirstName(), customer.getLastName());
        Customer customer1 = customerService.findCustomerById(id);
        Response response = new Response();
        if (customer1 != null) {
            customer1.setFirstName(customer.getFirstName());
            customer1.setLastName(customer.getLastName());
            customerService.createCustomer(customer1);
            logger.info("Updated customer: {} {}", customer1.getFirstName(), customer1.getLastName());
            PrimaryData data = new PrimaryData();
            data.customer = customer;
            response.primaryData = data;
            response.responseHeader = new ResponseHeader(SuccessFailureEnums.SUCCESS_CODE, SuccessFailureEnums.SUCCESS_STATUS_MESSAGE,
                    SuccessFailureEnums.SUCCESS_MESSAGE_CODE, SuccessFailureEnums.SUCCESS_MESSAGE_DESCRIPTION);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response> findCustomerById(@PathVariable Long id) {
        logger.info("Find customer by id: {}", id);
        Customer cust = customerService.findCustomerById(id);
        Response response = new Response();
        PrimaryData data = new PrimaryData();
        data.customer = cust;
        response.primaryData = data;
        response.responseHeader = new ResponseHeader(SuccessFailureEnums.SUCCESS_CODE, SuccessFailureEnums.SUCCESS_STATUS_MESSAGE,
                SuccessFailureEnums.SUCCESS_MESSAGE_CODE, SuccessFailureEnums.SUCCESS_MESSAGE_DESCRIPTION);
        logger.info("Found customer: {}", cust);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        logger.info("Find all customers");
        List<Customer> customers = customerService.findAllCustomers(page,size);
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
