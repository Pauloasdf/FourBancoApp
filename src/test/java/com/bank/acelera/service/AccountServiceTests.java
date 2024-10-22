/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.CheckingAccount;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.model.SavingsAccount;
import org.assertj.core.api.Assertions;
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
    private AccountService accountService;

    @Test
    public void whenCheckingAccount_thenSuccess() {
        // give
        Account account = accountService.openCheckingAccount(1L, "PasSwOrd");

        // then
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account).isInstanceOf(CheckingAccount.class);
        Assertions.assertThat(account.isOpened()).isTrue();
    }

    @Test
    public void whenSavingsAccount_thenSuccess() {
        // give
        Account account = accountService.openSavingsAccount(1L, "PasSwOrd");

        // then
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account).isInstanceOf(SavingsAccount.class);
        Assertions.assertThat(account.isOpened()).isTrue();
    }

    @Test
    public void whenAddingAMovementToAClosedAccount_thenDoesNotAllowOperation() throws UnsupportedOperationException {
        // given 
        String password = "PasSwOrd";
        Account account = new CheckingAccount();
        account.open(
                accountService.genareteNumberChecking(),
                password,
                new Physical("Jo�o alfredo", "123.123.123-53"));

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
        Account account = new CheckingAccount();
        account.open(
                accountService.genareteNumberChecking(),
                password,
                new Physical("Jo�o alfredo", "123.123.123-53"));

        // when
        boolean allowed = accountService.allowedMovement(account, new Movement(10.00F, Movement.Type.DEBIT));

        // then
        Assertions.assertThat(allowed).isFalse();
    }
}
