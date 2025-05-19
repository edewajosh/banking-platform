package com.bank.customers.controller;

import com.bank.customers.dto.PrimaryData;
import com.bank.customers.dto.Response;
import com.bank.customers.entities.Customer;
import com.bank.customers.service.CustomerService;
import com.bank.customers.utils.ResponseHeader;
import com.bank.customers.utils.SuccessFailureEnums;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    CustomerService customerService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createCustomer_ShouldReturnCustomer() throws Exception {
        Customer customer = getCustomerInfo();
        Response result = new Response();
        result.responseHeader = new ResponseHeader(SuccessFailureEnums.SUCCESS_CODE, SuccessFailureEnums.SUCCESS_STATUS_MESSAGE,
                SuccessFailureEnums.SUCCESS_MESSAGE_CODE, SuccessFailureEnums.SUCCESS_MESSAGE_DESCRIPTION);
        PrimaryData data = new PrimaryData();
        data.customer = customer;
        result.primaryData = data;
        Customer request = new Customer("Jane", "Doe", null);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(result);
        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)
                ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("primaryData.customer.otherName").isEmpty())
                .andExpect(jsonPath("primaryData.customer.firstName").value(request.getFirstName()));
        verify(customerService).createCustomer(any(Customer.class));
    }

    @Test
    void createCustomer_NullBody_ShouldReturnBadRequest() throws Exception {
        when(customerService.createCustomer(any(Customer.class))).thenThrow(new RuntimeException());
        mockMvc.perform(post("/api/v1/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_ShouldThrowHttpRequestMethodNotSupportedException() throws Exception {
        Customer request = new Customer("Jane", "Doe", null);
        when(customerService.createCustomer(any(Customer.class))).thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)
                        ))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void getCustomerInfo_ShouldReturnCustomer() throws Exception {
        Customer result = getCustomerInfo();
        when(customerService.findCustomerById(anyLong())).thenReturn(result);
        mockMvc.perform(get("/api/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(result.getId()))
                .andExpect(jsonPath("firstName").value(result.getFirstName()));
    }

    @Test
    void getCustomerInfo_InvalidId_ShouldReturnNull() throws Exception {
        when(customerService.findCustomerById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/api/v1/customer/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").doesNotExist())
                .andExpect(jsonPath("firstName").doesNotExist());
    }
    Customer getCustomerInfo(){
        return new Customer(1L, "Jane", "Doe", null);
    }
}
