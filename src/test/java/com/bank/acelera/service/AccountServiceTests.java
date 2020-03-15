/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.repository.PhysicalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author lauro
 */
@SpringBootTest
public class AccountServiceTests {

    @Autowired
    private PhysicalRepository physicalRepository;

    @Autowired
    private AccountService accountService;

    private Physical physical;

    @BeforeEach
    public void setUp() {

        if (physicalRepository.findByName("João alfredo") == null) {
            this.physical = new Physical();
            this.physical.setName("João alfredo");
            this.physical.setCpf("123.123.123-53");
            this.physical = physicalRepository.save(this.physical);
        } else {
            this.physical = physicalRepository.findByName("João alfredo");
        }
    }
    
    @Test
    public void whenOpenAccount_thenSuccess(){
        // give
        Account account = accountService.open(1L,"PasSwOrd");
                
        // then
        Assertions.assertThat(account).isNotNull();
    }

    @Test
    public void whenAddingAMovementToAClosedAccount_thenDoesNotAllowOperation() throws UnsupportedOperationException {
        // given 
        String password = "PasSwOrd";
        Account account = new Account();
        account.open(11111113L, password, physical);
        
        // when
        account.close(password);
        boolean allowed = accountService.allowedMovement(account, new Movement(10.00F, Movement.Type.CREDIT));

        // then
        Assertions.assertThat(allowed).isFalse();
    }

    @Test
    public void whenAddingAMovementNoAccountBalance_thenDoesNotAllowOperation() {
        // given
        String password = "PasSwOrd";
        Account account = new Account();
        account.open(11111113L, password, physical);

        // when
        boolean allowed = accountService.allowedMovement(account, new Movement(10.00F, Movement.Type.DEBIT));

        // then
        Assertions.assertThat(allowed).isFalse();
    }

}
