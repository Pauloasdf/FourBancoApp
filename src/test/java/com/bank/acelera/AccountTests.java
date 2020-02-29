package com.bank.acelera;

import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import com.bank.acelera.repository.AccountRepository;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.bank.acelera.service.AccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bank.acelera.repository.PhysicalRepository;

@SpringBootTest
class AccountTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PhysicalRepository physicalRepository;

    @Autowired
    private AccountService accountService;

    private Validator validator;

    private Physical physical;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

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
    public void whenNullPasswordAndPerson_thenTwoConstraintViolations() {
        // given
        Account account = new Account();

        // when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        // then
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    public void whenNullPerson_thenOneConstraintViolations() {
        // given
        Account account = new Account();
        account.open(111111L, "PasSwOrd", null);

        // when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        // then
        Assertions.assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenFindById_thenOpenDateNotNull() {
        // given
        Account account = new Account();
        account.open(11111112L, "PasSwOrd", this.physical);
        accountRepository.save(account);

        // when
        Account found = accountRepository.findById(account.getId()).get();

        // then
        Assertions.assertThat(found.getOpenDate())
                .isNotNull();
    }
    
    @Test
    public void whenAddingAMovementToAClosedAccount_thenDoesNotAllowOperation() {
        // 
        String password = "PasSwOrd";

        Account account = new Account();
        account.open(11111113L, password, physical);
                
        // given
        account.close(password);

        // when
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
    
    @Test
    public void whenAddingMovements_thenTheBalanceMustBeUpdated() {
        // given
        String password = "PasSwOrd";

        Account account = new Account();
        account.open(11111113L, password, physical);
        accountRepository.save(account);

        // when
        accountService.addMovement(account.getNumber(), new Movement(10.00F, Movement.Type.CREDIT));
        float balance0 = account.getBalance();

        account = accountRepository.getOne(account.getId());
        float balance10 = account.getBalance();

        Account found1 = accountRepository.getOne(account.getId());

        accountService.addMovement(found1.getNumber(), new Movement(5.00F, Movement.Type.DEBIT));
        found1 = accountRepository.getOne(found1.getId());
        float balance5 = found1.getBalance();

        Account found2 = accountRepository.getOne(account.getId());

        accountService.addMovement(found2.getNumber(), new Movement(20.00F, Movement.Type.CREDIT));
        found2 = accountRepository.getOne(found2.getId());
        float balance25 = found2.getBalance();

        // then
        Assertions.assertThat(balance0).isEqualTo(0.00F);
        Assertions.assertThat(balance10).isEqualTo(10.00F);
        Assertions.assertThat(balance5).isEqualTo(5.00F);
        Assertions.assertThat(balance25).isEqualTo(25.00F);
    }
}