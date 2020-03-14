/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.controller;

import com.bank.acelera.service.AccountService;
import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.model.Movement;
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
//@WebMvcTest(MovementController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovementControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    AccountService accountService;
    
    // Used for converting heroes to/from JSON
    private ObjectMapper mapper = new ObjectMapper();
    
    @Test
    void apiGetMovements() throws Exception {
        mvc.perform(get("/transaction/22222223")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", Matchers.hasSize(11)))
                .andExpect(jsonPath("$[0].value", Matchers.is(10.0)));
    }
    
    @Test
    void whenTheTypeIsNotValid_thenStatus400() throws Exception {

        MovementRequest movementRequest = new MovementRequest();
        movementRequest.setAccountNumber(22222223L);
        movementRequest.setType(4);
        movementRequest.setValue(10.0F);

        byte[] movementJson = toJson(movementRequest);

        mvc.perform(
                post("/transaction")
                .content(movementJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
         .andExpect(content().string(Movement.TYPE_NOT_VALID));
    }
    
    @Test
    void whenNullValue_thenStatus400() throws Exception {

        MovementRequest movementRequest = new MovementRequest();
        movementRequest.setAccountNumber(22222223L);
        movementRequest.setType(2);

        byte[] movementJson = toJson(movementRequest);

        mvc.perform(
                post("/transaction")
                .content(movementJson)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest())
         .andExpect(content().string(Movement.VALUE_NOT_NUMBER));
    }

    @Test
    void apiCreateNewMovement() throws Exception {

        MovementRequest movementRequest = new MovementRequest();
        movementRequest.setAccountNumber(22222223L);
        movementRequest.setType(2);
        movementRequest.setValue(10.0F);

        byte[] movementJson = toJson(movementRequest);

        mvc.perform(
                post("/transaction")
                .content(movementJson)
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
