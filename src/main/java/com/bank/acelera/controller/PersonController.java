package com.bank.acelera.controller;

import com.bank.acelera.controller.request.PersonRequest;
import com.bank.acelera.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;
    
    @PostMapping("/create")
    public ResponseEntity createNewPerson(@RequestBody PersonRequest personRequest){
        try {
            if(personService.create(personRequest)){
                return ResponseEntity.status(HttpStatus.CREATED).body(personRequest);
            }
        } catch (Exception e) {
            if(e instanceof IllegalArgumentException){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();        
    }
}
