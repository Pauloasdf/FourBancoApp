/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.model.abstrac;

import com.bank.acelera.model.Movement;
import com.bank.acelera.model.abstrac.Person;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lauro
 */
@Entity
@Inheritance
public abstract class Account {
    
    public static String INVALID_PASSWORD = "Invalid password";
    
    public static String ACCOUNT_CLOSED = "Account already closed|";
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(unique = true)
    private long number;

    private Date openDate;
    
    private Date closeDate;
    
    @NotBlank
    private String password;
    
    private float balance;
    
    @OneToMany(mappedBy = "account")
    private List<Movement> movements;
    
    @NotNull()
    @ManyToOne
    @JoinColumn(name="person_id", nullable = false)
    private Person person;
    
    public int getId() {
        return id;
    } 
    
    public long getNumber() {
        return number;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public float getBalance() {
        return balance;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public Person getPerson() {
        return person;
    }
    
    /**
     * Open Account
     * 
     * @param number
     * @param password
     * @param person 
     */
    public void open(Long number,String password,Person person) {
        if(!this.validPassword(password)){
            throw new IllegalArgumentException(INVALID_PASSWORD);
        }

        this.password = this.encrypt(password);
        this.openDate = new Date();
        this.person = person;
        this.number = number;
    }
    
    /**
     * If the account it's opened
     * 
     * @return 
     */
    public boolean isOpened(){
        return this.openDate != null && this.closeDate == null;
    }
    
    /**
     * Close Account
     * 
     * @param password
     * @return 
     */
    public boolean close(String password) throws UnsupportedOperationException {
        // to avoid changes to the account closing date
        if(!this.isOpened()){
            throw new UnsupportedOperationException(ACCOUNT_CLOSED);
        }
        
        if(this.checkPassword(password)){
            this.closeDate = new Date();
        }
        return this.closeDate != null;
    }
    
    /**
     * Reduce balance
     * @param valeu
     */
    public void reduce(float value){
        this.balance = this.balance - value;
    }
    
    /**
     * Increment balance
     * @param value 
     */
    public void increment(float value){
        this.balance = this.balance + value;
    }
    
    /**
     * valid password
     * 
     * @param password
     * @return 
     */
    private boolean validPassword(String password) {      
        return password != null && !password.isEmpty() && password.length() == 8;
    }
        
    /**
     * Check password
     * 
     * @param password
     * @return 
     */
    private boolean checkPassword(String password){
        return this.password.equals(this.encrypt(password));
    }
    
    /**
     * Encrypt the password
     * @param password
     * @return 
     */
    private String encrypt(String password) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(),0,password.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
        }
        return password;
    }
}
