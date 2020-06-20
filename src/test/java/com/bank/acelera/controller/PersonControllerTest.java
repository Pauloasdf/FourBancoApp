/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.controller;

import com.bank.acelera.service.AccountService;
import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.model.Legal;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.model.abstrac.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author lauro
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    AccountService accountService;
    
    // Used for converting heroes to/from JSON
    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    void createPhysical() throws Exception {
                
        byte[] personJson = toJson(new Physical("Joao Alfredo","123.123.123-56"));
        
        mvc.perform(
                post("/person/create")
                .content(personJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
    
    @Test
    void createLegal() throws Exception {
                
        byte[] personJson = toJson(new Legal("Acelera LTDA","28.249.305/0001-50"));
        
        mvc.perform(
                post("/person/create")
                .content(personJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
    
    /**
     * Convert object to JSON bytes.
     * 
     * @param object
     *            The object to JSONify
     * @return byte array with JSON representation
     * @throws Exception
     */
    private byte[] toJson(Object object) throws Exception {
        return this.mapper.writeValueAsString(object).getBytes();
    }

}
