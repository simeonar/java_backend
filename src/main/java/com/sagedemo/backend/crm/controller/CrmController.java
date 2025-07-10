package com.sagedemo.backend.crm.controller;

import com.sagedemo.backend.crm.dto.ContactDTO;
import com.sagedemo.backend.crm.service.CrmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crm/contacts")
public class CrmController {

    private final CrmService crmService;

    public CrmController(CrmService crmService) {
        this.crmService = crmService;
    }

    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO createdContact = crmService.createContact(contactDTO);
        return ResponseEntity.ok(createdContact);
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        List<ContactDTO> contacts = crmService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
}
