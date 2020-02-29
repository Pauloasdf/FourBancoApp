package com.bank.acelera.controller;

import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
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
    private MovementService movementService;

    public TransactionsController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public Movement createNewTransaction(@RequestBody Movement movement){
        System.out.println(movement);
        return movement;
    }

    @GetMapping
    public ResponseEntity getTransaction(@PathVariable Long accountNumber){
        // TODO
        return ResponseEntity.status(HttpStatus.OK).body("get");
    }
}
