package com.sagedemo.backend.crm.service;

import com.sagedemo.backend.crm.dto.ContactDTO;
import com.sagedemo.backend.crm.entity.Contact;
import com.sagedemo.backend.crm.repository.ContactRepository;
import com.sagedemo.backend.sales.entity.Customer;
import com.sagedemo.backend.sales.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final CustomerRepository customerRepository;

    public CrmService(ContactRepository contactRepository, CustomerRepository customerRepository) {
        this.contactRepository = contactRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhone(contactDTO.getPhone());

        if (contactDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(contactDTO.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            contact.setCustomer(customer);
        }

        Contact savedContact = contactRepository.save(contact);
        return toDto(savedContact);
    }

    @Transactional(readOnly = true)
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ContactDTO toDto(Contact contact) {
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());
        if (contact.getCustomer() != null) {
            dto.setCustomerId(contact.getCustomer().getId());
        }
        return dto;
    }
}
