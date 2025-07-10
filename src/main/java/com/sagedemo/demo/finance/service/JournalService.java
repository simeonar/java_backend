package com.sagedemo.demo.finance.service;

import com.sagedemo.demo.finance.entity.Journal;
import com.sagedemo.demo.finance.entity.Transaction;
import com.sagedemo.demo.finance.repository.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService {
    private final JournalRepository journalRepository;
    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }
    @Transactional
    public Journal createJournalEntry(Transaction transaction, String description, String createdBy) {
        Journal journal = new Journal();
        journal.setEntryDate(LocalDateTime.now());
        journal.setTransaction(transaction);
        journal.setDescription(description);
        journal.setCreatedBy(createdBy);
        return journalRepository.save(journal);
    }
    public List<Journal> getAllEntries() {
        return journalRepository.findAll();
    }
}
