package com.bank.acelera.controller;

import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.controller.response.MovementResponse;
import com.bank.acelera.service.AccountService;
import com.bank.acelera.service.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/transaction")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @PostMapping
    public ResponseEntity createNewMovement(@RequestBody MovementRequest movementRequest){

        try {
            if(movementService.addMovement(movementRequest)){
                return ResponseEntity.status(HttpStatus.CREATED).body("Realizada com sucesso");
            }
        } catch (Exception e) {
            if(e instanceof IllegalArgumentException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping(path = "/{accountNumber}")
    public ResponseEntity getMovements(@PathVariable Long accountNumber){
        
        try {
         
            List<MovementResponse> movementResponses =  movementService.getMovements(accountNumber);
            if (!movementResponses.equals(null)){
               return ResponseEntity.status(HttpStatus.OK).body(movementResponses);
            }
         
        } catch (Exception e) {
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
