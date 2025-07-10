package com.sagedemo.demo.finance.service;

import com.sagedemo.demo.finance.dto.BalanceReportDTO;
import com.sagedemo.demo.finance.dto.ProfitLossReportDTO;
import com.sagedemo.demo.finance.entity.Account;
import com.sagedemo.demo.finance.entity.AccountType;
import com.sagedemo.demo.finance.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialReportService {
    private final AccountRepository accountRepository;

    public FinancialReportService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public BalanceReportDTO getBalanceReport() {
        List<Account> accounts = accountRepository.findAll();
        Map<String, BigDecimal> assets = new HashMap<>();
        Map<String, BigDecimal> liabilities = new HashMap<>();
        Map<String, BigDecimal> equity = new HashMap<>();
        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalLiabilities = BigDecimal.ZERO;
        BigDecimal totalEquity = BigDecimal.ZERO;
        for (Account acc : accounts) {
            if (acc.getType() == AccountType.ASSET) {
                assets.put(acc.getAccountName(), acc.getBalance());
                totalAssets = totalAssets.add(acc.getBalance());
            } else if (acc.getType() == AccountType.LIABILITY) {
                liabilities.put(acc.getAccountName(), acc.getBalance());
                totalLiabilities = totalLiabilities.add(acc.getBalance());
            } else if (acc.getType() == AccountType.EQUITY) {
                equity.put(acc.getAccountName(), acc.getBalance());
                totalEquity = totalEquity.add(acc.getBalance());
            }
        }
        BalanceReportDTO dto = new BalanceReportDTO();
        dto.setAssets(assets);
        dto.setLiabilities(liabilities);
        dto.setEquity(equity);
        dto.setTotalAssets(totalAssets);
        dto.setTotalLiabilities(totalLiabilities);
        dto.setTotalEquity(totalEquity);
        return dto;
    }

    public ProfitLossReportDTO getProfitLossReport() {
        List<Account> accounts = accountRepository.findAll();
        Map<String, BigDecimal> revenues = new HashMap<>();
        Map<String, BigDecimal> expenses = new HashMap<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        for (Account acc : accounts) {
            if (acc.getType() == AccountType.REVENUE) {
                revenues.put(acc.getAccountName(), acc.getBalance());
                totalRevenue = totalRevenue.add(acc.getBalance());
            } else if (acc.getType() == AccountType.EXPENSE) {
                expenses.put(acc.getAccountName(), acc.getBalance());
                totalExpense = totalExpense.add(acc.getBalance());
            }
        }
        ProfitLossReportDTO dto = new ProfitLossReportDTO();
        dto.setRevenues(revenues);
        dto.setExpenses(expenses);
        dto.setTotalRevenue(totalRevenue);
        dto.setTotalExpense(totalExpense);
        dto.setNetProfit(totalRevenue.subtract(totalExpense));
        return dto;
    }
}
