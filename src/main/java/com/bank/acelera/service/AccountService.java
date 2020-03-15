/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.abstrac.Person;
import com.bank.acelera.repository.AccountRepository;
import java.util.Calendar;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
        
    @Autowired
    private AccountNumberService accountNumberService;
    
    @Autowired
    private PersonService personService;
    

    /**
     * find by number
     * 
     * @param number
     * @return 
     */
    public Account findByNumber(long number) throws IllegalArgumentException {
        
        Optional<Account> found = accountRepository.findByNumber(number);
        
        if(found.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }
        
        return found.get();
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

    /**
     * 
     * @param personId
     * @param pasSwOrd
     * @return 
     */
    public Account open(long personId, String pasSwOrd) {
        Person person = personService.findById(personId);
        Account account = new Account();
        account.open(this.genareteNumber(), pasSwOrd, person);
        this.save(account);        
        return account;
    }

    /**
     * 
     * @return 
     */
    private Long genareteNumber() {
        Calendar cal = Calendar.getInstance();
        Integer year = cal.get(Calendar.YEAR);
        Long next = accountNumberService.nextNumber();
        return Long.parseLong(year.toString() + String.format("%04d", next));
    }
}
