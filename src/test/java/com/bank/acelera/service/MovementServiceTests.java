package com.bank.acelera.service;

import com.bank.acelera.model.Account;
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

        Account account = new Account();
        account.open(11111113L, password, physical);
        accountRepository.save(account);

        // when
        movementService.addMovement(account.getNumber(), new Movement(10.00F, Movement.Type.CREDIT));
        float balance0 = account.getBalance();

        account = accountRepository.getOne(account.getId());
        float balance10 = account.getBalance();

        Account found1 = accountRepository.getOne(account.getId());

        movementService.addMovement(found1.getNumber(), new Movement(3.00F, Movement.Type.DEBIT));
        found1 = accountRepository.getOne(found1.getId());
        float balance7 = found1.getBalance();

        Account found2 = accountRepository.getOne(account.getId());

        movementService.addMovement(found2.getNumber(), new Movement(20.00F, Movement.Type.CREDIT));
        found2 = accountRepository.getOne(found2.getId());
        float balance25 = found2.getBalance();

        // then
        Assertions.assertThat(balance0).isEqualTo(0.00F);
        Assertions.assertThat(balance10).isEqualTo(10.00F);
        Assertions.assertThat(balance7).isEqualTo(7.00F);
        Assertions.assertThat(balance25).isEqualTo(27.00F);
    }
}
