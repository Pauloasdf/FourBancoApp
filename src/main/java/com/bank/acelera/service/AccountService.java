/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.repository.AccountRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
public class AccountService {
    
    @Autowired
    MovementService movementService;
    
    @Autowired
    AccountRepository accountRepository;
    
    
    /**
     * find by number
     * 
     * @param number
     * @return 
     */
    public Account findByNumber(long number){
        return accountRepository.findByNumber(number);
    }
    
    /**
     * Save
     * @param account
     * @return 
     */
    public Account save(Account account) {
        return accountRepository.save(account);
    }
    
    /**
     * Add movement
     * 
     * @param movement
     * @return
     */
    public boolean addMovements(long number, Movement movement) {
        Account account = this.findByNumber(number);
        
        if(account != null && account.allowedMovement(movement)) {
            
            movement.setAccount(account);
            
            try {
                movement = movementService.save(movement);
                account.calculateTheBalance(movement);
                this.save(account);
                return true;
            } catch (Exception e) {
                return false;
            }            
        }
        return false;
    }
    
}
