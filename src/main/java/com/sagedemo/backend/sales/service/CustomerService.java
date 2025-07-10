package com.sagedemo.backend.sales.service;

import com.sagedemo.backend.sales.dto.CustomerDTO;
import com.sagedemo.backend.sales.entity.Customer;
import com.sagedemo.backend.sales.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerNumber(customerDTO.getCustomerNumber());
        customer.setCompanyName(customerDTO.getCompanyName());
        customer.setContactPerson(customerDTO.getContactPerson());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());
        Customer savedCustomer = customerRepository.save(customer);
        return toDto(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CustomerDTO toDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setCustomerNumber(customer.getCustomerNumber());
        dto.setCompanyName(customer.getCompanyName());
        dto.setContactPerson(customer.getContactPerson());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setAddress(customer.getAddress());
        return dto;
    }
}
