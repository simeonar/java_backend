package com.sagedemo.demo.crm.service;

import com.sagedemo.demo.crm.entity.Lead;
import com.sagedemo.demo.crm.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeadService {
    private final LeadRepository leadRepository;

    public LeadService(LeadRepository leadRepository) {
        this.leadRepository = leadRepository;
    }

    public List<Lead> findAll() {
        return leadRepository.findAll();
    }

    public Optional<Lead> findById(Long id) {
        return leadRepository.findById(id);
    }

    @Transactional
    public Lead save(Lead lead) {
        return leadRepository.save(lead);
    }

    @Transactional
    public Optional<Lead> update(Long id, Lead updated) {
        return leadRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setPhone(updated.getPhone());
            existing.setCompany(updated.getCompany());
            existing.setStatus(updated.getStatus());
            return leadRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (leadRepository.existsById(id)) {
            leadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
