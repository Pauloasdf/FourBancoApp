package com.bank.acelera;

import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Person;
import com.bank.acelera.repository.AccountRepository;
import com.bank.acelera.repository.PersonRepository;
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

@SpringBootTest
class AccountTests {
 
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PersonRepository personRepository;
    
    private Validator validator;
    
    private Person person = new Person();
    
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        
        person.setName("Teste");
        personRepository.save(person);
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
        account.open(111111L,"PasSwOrd",null);
        
        // when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        
        // then
        Assertions.assertThat(violations.size()).isEqualTo(1);
    }
    
    @Test
    public void whenFindById_thenOpenDateNotNull() {
        // given
        Account account = new Account();
        account.open(11111112L,"PasSwOrd",person);
        accountRepository.save(account);
                
        // when
        Account found = accountRepository.findById(account.getId()).get();

        // then
        Assertions.assertThat(found.getOpenDate())
          .isNotNull();
    }
    
    @Test
    public void whenAddingMovements_thenTheBalanceMustBeUpdated() {
        // given
        String password = "PasSwOrd";
        AccountService accountService = new AccountService();
        Account account = new Account();
        account.open(11111113L,password,person);
        
        // when
        accountService.addMovements(account.getNumber(), new Movement(10.00F, Movement.Type.CREDIT));
        accountRepository.save(account);
        
        Account found1 = accountRepository.getOne(account.getId());
        accountService.addMovements(account.getNumber(), new Movement(5.00F, Movement.Type.DEBIT));
        accountRepository.save(found1);
        
        Account found2 = accountRepository.getOne(account.getId());
        accountService.addMovements(account.getNumber(), new Movement(20.00F, Movement.Type.CREDIT));
        accountRepository.save(found2);

        // then
        Assertions.assertThat(account.getBalance()).isEqualTo(10.00F);
        Assertions.assertThat(found1.getBalance()).isEqualTo(5.00F);
        Assertions.assertThat(found2.getBalance()).isEqualTo(25.00F);
    }

}
