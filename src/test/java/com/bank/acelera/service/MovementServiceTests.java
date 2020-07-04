package com.bank.acelera.service;

import com.bank.acelera.model.CheckingAccount;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.repository.account.AccountRepository;
import com.bank.acelera.repository.person.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovementServiceTests {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private MovementService movementService;
    
    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        if (personRepository.findByName("João alfredo") == null) {
            this.physical = new Physical("João alfredo", "123.123.123-53");
            this.physical = personRepository.save(this.physical);
        } else {
            this.physical = (Physical) personRepository.findByName("João alfredo");
        }
    }
    private Physical physical;

    @Test
    public void whenAddingMovements_thenTheBalanceMustBeUpdated() {
        // given
        String password = "PasSwOrd";

        Account account = new CheckingAccount();
        account.open(accountService.genareteNumberChecking(), password, physical);
        accountRepository.save(account);
        
        // when
        float balance0 = account.getBalance();
        
        movementService.addMovement(account.getNumber(), new Movement(10.00F, Movement.Type.CREDIT));
        float balance10 = accountRepository.getOne(account.getId()).getBalance();

        movementService.addMovement(account.getNumber(), new Movement(3.00F, Movement.Type.DEBIT));
        float balance7 = accountRepository.getOne(account.getId()).getBalance();

        movementService.addMovement(account.getNumber(), new Movement(20.00F, Movement.Type.CREDIT));
        float balance27 = accountRepository.getOne(account.getId()).getBalance();

        // then
        Assertions.assertThat(balance0).isEqualTo(0.00F);
        Assertions.assertThat(balance10).isEqualTo(10.00F);
        Assertions.assertThat(balance7).isEqualTo(7.00F);
        Assertions.assertThat(balance27).isEqualTo(27.00F);
    }
}
