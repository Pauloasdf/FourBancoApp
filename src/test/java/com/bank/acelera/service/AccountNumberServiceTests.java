/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author lauro
 */
@SpringBootTest
public class AccountNumberServiceTests {

    @Autowired
    private AccountNumberService accountNumberService;


    @Test
    public void testAccountNumber() {
        // given 
        Long number = accountNumberService.genareteNumber(accountNumberService.SEGUENCE_TYPE_CHECKING_ACCOUNT);        

        // when
        Long nextNumber = accountNumberService.genareteNumber(accountNumberService.SEGUENCE_TYPE_CHECKING_ACCOUNT);

        // then
        Assertions.assertThat(number + 1).isEqualTo(nextNumber);
        Assertions.assertThat(number.toString().length()).isEqualTo(9);
    }
}
