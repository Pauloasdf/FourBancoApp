/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.controller;

import com.bank.acelera.service.AccountService;
import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.model.CheckingAccount;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.abstrac.Person;
import com.bank.acelera.repository.account.AccountRepository;
import com.bank.acelera.repository.account.MovementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    AccountService accountService;
    
    @MockBean
    MovementRepository movementRepository;
    
    // Used for converting Objetc to/from JSON
    private ObjectMapper mapper = new ObjectMapper();
    
    public List<Movement> getFakeList() {
        List<Movement> movements = new LinkedList<>();
        movements.add(new Movement(50.00F, Movement.Type.CREDIT));
        movements.add(new Movement(10.00F, Movement.Type.DEBIT));
        movements.add(new Movement(15.00F, Movement.Type.CREDIT));
        movements.add(new Movement(10.00F, Movement.Type.DEBIT));
        movements.add(new Movement(20.00F, Movement.Type.CREDIT));
        movements.add(new Movement(10.00F, Movement.Type.CREDIT));
        movements.add(new Movement(10.00F, Movement.Type.CREDIT));
        return movements;
    }
    
    @Test
    void apiGetMovements() throws Exception {
        
        Long accountNumber = 22222223L;
        
        // mock
        when( movementRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(getFakeList()));
        
        mvc.perform(get("/transaction/"+accountNumber.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", Matchers.is(7)))
                .andExpect(jsonPath("$[0].value", Matchers.is(50.0)));
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
        movementRequest.setType(MovementRequest.Type.DEBIT.ordinal());

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
        Long accountNumber = 22222223L;
        Account account = new CheckingAccount();
        Person person = new Physical();
        account.open(accountNumber, "PaSsWoRd", person);

        MovementRequest movementRequest = new MovementRequest();
        movementRequest.setAccountNumber(accountNumber);
        movementRequest.setType(MovementRequest.Type.CREDIT.ordinal());
        movementRequest.setValue(11.0F);
        
        when(accountService.findByNumber(accountNumber)).thenReturn(account);
        when(accountService.allowedMovement(account, new Movement())).thenReturn(true);
        when(accountService.save(account)).thenReturn(account);        

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
