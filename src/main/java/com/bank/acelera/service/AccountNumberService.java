/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.AccountNumber;
import com.bank.acelera.repository.AccountNumberRepository;
import java.util.Calendar;
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
    private AccountNumber newAccountNumber(int type){
        AccountNumber number = new AccountNumber();
        number.setId(type);
        number.setSeguence(0L);
        return this.save(number);
    }

    /**
     * 
     * @return 
     */
    private Long nextNumber(int type) {
        Optional<AccountNumber> op = accountNumberRepository.findById(type);
        AccountNumber number = op.isEmpty() ? newAccountNumber(type) : op.get();
        number.incremente();
        this.save(number);
        return number.getSeguence();
    }
    
    /**
     * Generate next number
     * @param type
     * @return 
     */
    public Long genareteNumber(int type){
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        Long next = this.nextNumber(type);
        
        return Long.parseLong(year.toString()+ type + String.format("%04d", next));
    }   
}
