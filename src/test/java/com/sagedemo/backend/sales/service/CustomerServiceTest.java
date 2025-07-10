package com.sagedemo.backend.sales.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sagedemo.backend.sales.entity.Customer;
import com.sagedemo.backend.sales.repository.CustomerRepository;

import java.util.Optional;

class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    public CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCompanyName("Test Company");
        when(customerRepository.findAll()).thenReturn(java.util.Collections.singletonList(customer));

        var result = customerService.getAllCustomers();
        assertEquals(1, result.size());
        assertEquals("Test Company", result.get(0).getCompanyName());
    }
}
