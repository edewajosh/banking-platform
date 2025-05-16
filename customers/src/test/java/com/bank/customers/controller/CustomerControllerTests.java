package com.bank.customers.controller;

import com.bank.customers.entities.Customer;
import com.bank.customers.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CustomerService customerService;
    @MockitoBean
    private ObjectMapper objectMapper;

    @Test
    void createCustomer_ShouldReturnCustomer() throws Exception {
        Customer result = getCustomerInfo();
        Customer request = new Customer("Jane", "Doe", null);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(result);
        mockMvc.perform(post("api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)
                ))
                .andExpect(status().isOk());
        verify(customerService).createCustomer(any(Customer.class));
    }

    Customer getCustomerInfo(){
        return new Customer(1L, "Jane", "Doe", null);
    }
}
