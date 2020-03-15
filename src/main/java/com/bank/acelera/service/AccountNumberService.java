/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.AccountNumber;
import com.bank.acelera.repository.AccountNumberRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
class AccountNumberService {
    
    @Autowired
    AccountNumberRepository accountNumberRepository;
    
    /**
     * 
     * @param number
     * @return 
     */
    private AccountNumber save(AccountNumber number){
        return accountNumberRepository.save(number);
    }
    
    /**
     * 
     * @return 
     */
    private AccountNumber newSeguence(){
        AccountNumber number = new AccountNumber();
        number.setId(1);
        number.setSeguence(0L);
        return this.save(number);
    }

    /**
     * 
     * @return 
     */
    public Long nextNumber() {
        AccountNumber number;
        Optional<AccountNumber> op = accountNumberRepository.findById(1);
        
        if(op.isEmpty()){
            number = newSeguence();
        } else {
            number = op.get();
        }
        
        number.incremente();
        this.save(number);
        return number.getSeguence();
    }
    
    
}
