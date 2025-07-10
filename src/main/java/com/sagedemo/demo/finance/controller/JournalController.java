package com.sagedemo.demo.finance.controller;

import com.sagedemo.demo.finance.entity.Journal;
import com.sagedemo.demo.finance.service.JournalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/finance/journal")
public class JournalController {
    private final JournalService journalService;
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }
    @GetMapping
    public List<Journal> getAllEntries() {
        return journalService.getAllEntries();
    }
}
