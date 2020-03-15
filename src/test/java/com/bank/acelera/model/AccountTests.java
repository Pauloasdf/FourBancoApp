package com.bank.acelera.model;

import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Physical;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bank.acelera.repository.PhysicalRepository;

@SpringBootTest
public class AccountTests {

    @Autowired
    private PhysicalRepository physicalRepository;

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
    public void whenNullPassword_thenIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> {
            // give
            Account account = new CheckingAccount();
            
            // when
            account.open(111111L, null, this.physical);

            // then
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Account.INVALID_PASSWORD);
    }
    
    @Test
    public void whenEmptyPassword_thenIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> {
            // give
            Account account = new CheckingAccount();

            // when            
            account.open(111111L, "", this.physical);

            // then
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Account.INVALID_PASSWORD);
    }

    @Test
    public void whenNullPasswordAndPerson_thenTwoConstraintViolations() {
        // given
        Account account = new CheckingAccount();

        // when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        // then
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    public void whenNullPerson_thenOneConstraintViolations() {
        // given
        Account account = new CheckingAccount();
        account.open(111111L, "PasSwOrd", null);

        // when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        // then
        Assertions.assertThat(violations.size()).isEqualTo(1);
    }
    
    @Test
    public void whenTwoCallsToTheCloseMethod_thenInvalidateOperation() {
                
         Assertions.assertThatThrownBy(() -> {
             
             // give
            Account account = new CheckingAccount();
            account.open(111111L, "PasSwOrd", this.physical);

            // when
            account.close("PasSwOrd");
            account.close("PasSwOrd");

            // then
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining(Account.ACCOUNT_CLOSED);
    }

}
