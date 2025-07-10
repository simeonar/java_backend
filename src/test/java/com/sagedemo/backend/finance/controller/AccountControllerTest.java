package com.sagedemo.backend.finance.controller;

import com.sagedemo.backend.finance.service.AccountService;
import com.sagedemo.backend.finance.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @Test
    @WithMockUser
    void testGetAllAccountsReturnsEmptyList() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/finance/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
