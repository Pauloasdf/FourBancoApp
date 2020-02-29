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
        
        if(account != null && this.allowedMovement(account, movement)) {
            
            movement.setAccount(account);
            
            try {
                movement = movementService.save(movement);
                this.calculateTheBalance(account, movement);
                this.save(account);
                return true;
            } catch (Exception e) {
                return false;
            }            
        }
        return false;
    }
    
    /**
     * Allowed movement
     * 
     * @param Moviment
     * @return 
     */
    public boolean allowedMovement(Account account, Movement movement){
        if(account.isOpened()){
            switch(movement.getType()){
                case CREDIT:
                    return true;
                case DEBIT:
                    return account.getBalance() > movement.getValue();
            }
        }
        return false;
    }
    
        /**
     * Calculate the balance
     * 
     * @param movement 
     */
    public void calculateTheBalance(Account account, Movement movement){
        if(account.isOpened()){
            switch(movement.getType()){
                case CREDIT:
                    account.increment(movement.getValue());
                   break;
                case DEBIT:
                    account.reduce(movement.getValue());
                    break;
            }
        } else {
            throw new IllegalArgumentException("Closed account");
        }
    }
}
