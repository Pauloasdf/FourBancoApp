package com.bank.acelera;

import com.bank.acelera.model.CheckingAccount;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.Physical;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import com.bank.acelera.repository.account.MovementRepository;
import com.bank.acelera.service.MovementService;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovementTests {

    @InjectMocks
    private MovementService movementService;

    @Mock
    private MovementRepository movementRepository;

    List<Movement> movements = new LinkedList<>();

    Date data = new Date();

    @BeforeEach
    public void init() {

        Physical person = new Physical();
        person.setName("João alfredo");
        person.setCpf("123.123.123-53");

        Account account = new CheckingAccount();
        account.open(11111112L, "PasSwOrd", person);

        Movement movement1 = new Movement(50.00F, Movement.Type.CREDIT);
        movement1.setDate(data);
        movement1.setAccount(account);

        data.setDate(data.getDate() - 1);
        Movement movement2 = new Movement(10.00F, Movement.Type.DEBIT);
        movement2.setDate(data);
        movement2.setAccount(account);

        data.setDate(data.getDate() - 1);
        Movement movement3 = new Movement(15.00F, Movement.Type.CREDIT);
        movement3.setDate(new Date());
        movement3.setAccount(account);

        data.setDate(data.getDate() - 1);
        Movement movement4 = new Movement(10.00F, Movement.Type.DEBIT);
        movement4.setDate(new Date());
        movement4.setAccount(account);

        data.setDate(data.getDate() - 1);
        Movement movement5 = new Movement(20.00F, Movement.Type.CREDIT);
        movement5.setDate(new Date());
        movement5.setAccount(account);

        data.setDate(data.getDate() - 1);
        Movement movement6 = new Movement(10.00F, Movement.Type.CREDIT);
        movement6.setDate(new Date());
        movement6.setAccount(account);

        data.setDate(data.getDate() - 1);
        Movement movement7 = new Movement(10.00F, Movement.Type.CREDIT);
        movement7.setDate(new Date());
        movement7.setAccount(account);

        movements.add(movement1);
        movements.add(movement2);
        movements.add(movement3);
        movements.add(movement4);
        movements.add(movement5);
        movements.add(movement6);
        movements.add(movement7);
    }

    @Test
    public void whenfindBetween_thenReturnMovement() {

        Date initialDate = new Date();
        Date finalDate = new Date();

        finalDate.setDate(finalDate.getDate() - 2);

        when(movementRepository
                .findByAccountNumberAndDateBetween(11111112L, initialDate, finalDate))
                .thenReturn(movements);

        List<Movement> resultMoviments = movementService
                .findBetween(
                        11111112L,
                        initialDate,
                        finalDate);

        Assertions
                .assertThat(resultMoviments)
                .isEqualTo(movements);
    }

}
