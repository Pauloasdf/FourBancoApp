package com.bank.acelera.controller;

import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.controller.response.MovementResponse;
import com.bank.acelera.model.Account;
import com.bank.acelera.repository.AccountRepository;
import com.bank.acelera.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class MovementController {

    private AccountService accountService;

    private AccountRepository accountRepository;

    public MovementController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity createNewMovement(@RequestBody MovementRequest movementRequest){

        if(accountService.addMovement(movementRequest)){
            return ResponseEntity.status(HttpStatus.OK).body("Realizada com sucesso");
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping(path = "/{accountNumber}")
    public ResponseEntity getMovements(@PathVariable Long accountNumber){
        List<MovementResponse> movementResponses =  accountService.getMovements(accountNumber);

        if (!movementResponses.equals(null)){
            return ResponseEntity.status(HttpStatus.OK).body(movementResponses);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
