package com.sagedemo.demo.crm.service;

import com.sagedemo.demo.crm.entity.Opportunity;
import com.sagedemo.demo.crm.repository.OpportunityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OpportunityService {
    private final OpportunityRepository opportunityRepository;

    public OpportunityService(OpportunityRepository opportunityRepository) {
        this.opportunityRepository = opportunityRepository;
    }

    public List<Opportunity> findAll() {
        return opportunityRepository.findAll();
    }

    public Optional<Opportunity> findById(Long id) {
        return opportunityRepository.findById(id);
    }

    @Transactional
    public Opportunity save(Opportunity opportunity) {
        return opportunityRepository.save(opportunity);
    }

    @Transactional
    public Optional<Opportunity> update(Long id, Opportunity updated) {
        return opportunityRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setStage(updated.getStage());
            existing.setValue(updated.getValue());
            existing.setLead(updated.getLead());
            return opportunityRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (opportunityRepository.existsById(id)) {
            opportunityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
