package com.bank.acelera.service;

import com.bank.acelera.model.CheckingAccount;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.repository.AccountRepository;
import com.bank.acelera.repository.PhysicalRepository;
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
    private PhysicalRepository physicalRepository;
    
    @Autowired
    private MovementService movementService;
    
    @Autowired
    private AccountNumberService accountNumberService;

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
    private Physical physical;

    @Test
    public void whenAddingMovements_thenTheBalanceMustBeUpdated() {
        // given
        String password = "PasSwOrd";

        Account account = new CheckingAccount();
        account.open(accountNumberService.genareteNumber(2), password, physical);
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
