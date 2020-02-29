package com.bank.acelera.controller.transaction;

import com.bank.acelera.controller.transaction.request.MovementRequest;
import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.repository.AccountRepository;
import com.bank.acelera.service.AccountService;
import com.bank.acelera.service.MovementService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

    private AccountService accountService;

    private AccountRepository accountRepository;

    public TransactionsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity createNewTransaction(@RequestBody MovementRequest movementRequest){

        if(accountService.addMovement(movementRequest)){
            return ResponseEntity.status(HttpStatus.OK).body("Realizada com sucesso");
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping(path = "/{accountNumber}")
    public ResponseEntity getTransaction(@PathVariable Long accountNumber){
        Account account =  accountService.findByNumber(accountNumber);

        if (!account.equals(null)){
            return ResponseEntity.status(HttpStatus.OK).body(account.getMovements());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
