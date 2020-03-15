/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.CheckingAccount;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.model.SavingsAccount;
import com.bank.acelera.model.abstrac.Person;
import com.bank.acelera.repository.AccountRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
public class AccountService {
    
    private static int SEGUENCE_TYPE_SAVINGS_ACCOUNT = 1;
    
    private static int SEGUENCE_TYPE_CHECKING_ACCOUNT = 2;
    
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
     * Open new savings account
     * @param personId
     * @param pasSwOrd
     * @return 
     */
    public Account openSavingsAccount(long personId, String pasSwOrd){
        return this.open(personId, pasSwOrd,this.genareteNumber(SEGUENCE_TYPE_SAVINGS_ACCOUNT), new SavingsAccount());
    }

    /**
     * Open new checking account
     * @param personId
     * @param pasSwOrd
     * @return 
     */
    public Account openCheckingAccount(long personId, String pasSwOrd){
        return this.open(personId, pasSwOrd,this.genareteNumber(SEGUENCE_TYPE_CHECKING_ACCOUNT), new CheckingAccount());
    }
    
    /**
     * 
     * @param personId
     * @param pasSwOrd
     * @return 
     */
    private Account open(long personId, String pasSwOrd, long number, Account account) {
        Person person = personService.findById(personId);
        account.open(number, pasSwOrd, person);
        this.save(account);
        return account;
    }

    /**
     * Generate next account number by type 
     * @param type
     * @return 
     */
    private Long genareteNumber(int type) {
        return accountNumberService.genareteNumber(type);
    }
}
