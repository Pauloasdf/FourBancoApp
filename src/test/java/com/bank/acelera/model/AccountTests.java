package com.bank.acelera.model;

import com.bank.acelera.model.abstrac.Account;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountTests {

    @Test
    public void whenNullPassword_thenIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> {
            // give
            Account account = new CheckingAccount();
            
            // when
            account.open(111111L, null, new Physical("João alfredo", "123.123.123-53"));

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
            account.open(111111L, "", new Physical("João alfredo", "123.123.123-53"));

            // then
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(Account.INVALID_PASSWORD);
    }

    @Test
    public void whenNullPasswordAndPerson_thenTwoConstraintViolations() {
        // given
        Account account = new CheckingAccount();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        
        // when
        Set<ConstraintViolation<Account>> violations = validator.validate(account);

        // then
        Assertions.assertThat(violations.size()).isEqualTo(2);
    }

    @Test
    public void whenNullPerson_thenOneConstraintViolations() {
        // given
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
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
            account.open(111111L, "PasSwOrd", new Physical("João alfredo", "123.123.123-53"));
            account.close("PasSwOrd");

            // when
            account.close("PasSwOrd");

            // then
        }).isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining(Account.ACCOUNT_CLOSED);
    }

}
