/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera;

import com.bank.acelera.service.AccountService;
import com.bank.acelera.controller.MovementController;
import com.bank.acelera.model.Account;
import com.bank.acelera.model.Physical;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author lauro
 */
@WebMvcTest(MovementController.class)
public class MovementControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    AccountService accountService;
    
    @Test
    @Sql({"/data.sql"})
    void apiGetMovements() throws Exception {
        mvc.perform(get("/transaction/22222223")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(11)))
                .andExpect(jsonPath("$[0].value", Matchers.is("10.0")));
    }


}
