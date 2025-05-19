package com.bank.customers.service;

import com.bank.customers.dto.PrimaryData;
import com.bank.customers.dto.Response;
import com.bank.customers.entities.Customer;
import com.bank.customers.repo.CustomerRepo;
import com.bank.customers.utils.ResponseHeader;
import com.bank.customers.utils.SuccessFailureEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Response createCustomer(Customer customer) {
        Response response = new Response();
        try {
            logger.info("Creating new customer: {}  {} ", customer.getFirstName(), customer.getLastName());
            Customer cust = customerRepo.save(customer) ;
            PrimaryData data = new PrimaryData();
            data.customer = cust;
            response.primaryData = data;
            response.responseHeader = new ResponseHeader(SuccessFailureEnums.SUCCESS_CODE, SuccessFailureEnums.SUCCESS_STATUS_MESSAGE,
                    SuccessFailureEnums.SUCCESS_MESSAGE_CODE, SuccessFailureEnums.SUCCESS_MESSAGE_DESCRIPTION);
        }catch (Exception e) {
            logger.error("Error occurred while creating {}", e.getMessage());
            response.responseHeader = new ResponseHeader(SuccessFailureEnums.FAILURE_CODE, SuccessFailureEnums.FAILURE_STATUS_MESSAGE,
                    "400", "Error occurred while creating");
            return response;
        }
        return response;
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
    public List<Customer> findAllCustomers(int page, int size) {
        // TODO Add logic to fetch accounts associated for each customer
        logger.info("Find all customers");
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Customer> customers = customerRepo.findAll(pageable);
            logger.info("Customers size: {}", customers.stream().toList().size());
            return customers.stream().toList();
        } catch (Exception e) {
            logger.error("Error occurred while fetching customer {}", e.getMessage());
            return null;
        }
    }
    public boolean deleteCustomer(Long id) {
        logger.info("Delete customer by id: {}", id);
        try {
            customerRepo.deleteById(id);
            return true;
        }catch (Exception e) {
            logger.error("Error occurred while deleting {}", e.getMessage());
            return false;
        }
    }
}
