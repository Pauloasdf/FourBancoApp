/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.controller;

import com.bank.acelera.service.AccountService;
import com.bank.acelera.controller.request.PersonRequest;
import com.bank.acelera.model.Legal;
import com.bank.acelera.model.Physical;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    
    // Used for converting Object to/from JSON
    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    void whenTheCpfIsEmpty_thenStatus400() throws Exception {

        PersonRequest person = new PersonRequest();
        person.setName("Joao Alfredo");
        person.setCpf("");
        person.setType(PersonRequest.Type.PHYSICAL);
        
        byte[] personJson = toJson(person);
        
        mvc.perform(
                post("/person/create")
                .content(personJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
         .andExpect(content().string(Physical.CPF_EMPTY));
    }
    
    @Test
    void createPhysical() throws Exception {

        PersonRequest person = new PersonRequest();
        person.setName("Joao Alfredo");
        person.setCpf("123.123.123-56");
        person.setType(PersonRequest.Type.PHYSICAL);
        
        mvc.perform(
                post("/person/create")
                .content(toJson(person))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
    
    @Test
    void whenTheCnpjIsEmpty_thenStatus400() throws Exception {

        PersonRequest person = new PersonRequest();
        person.setName("Aceleraca LTDA");
        person.setCnpj("");
        person.setType(PersonRequest.Type.LEGAL);
                
        mvc.perform(
                post("/person/create")
                .content(toJson(person))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
         .andExpect(content().string(Legal.CNPJ_EMPTY));
    }
    
    @Test
    void createLegal() throws Exception {
                
        PersonRequest person = new PersonRequest();
        person.setName("Acelera LTDA");
        person.setCnpj("28.249.305/0001-50");
        person.setType(PersonRequest.Type.LEGAL);
        
        mvc.perform(
                post("/person/create")
                .content(toJson(person))
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
