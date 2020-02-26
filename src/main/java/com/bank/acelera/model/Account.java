/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.model;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author lauro
 */
@Entity
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @Column(unique = true)
    private long number;
    
    private Date openDate;
    
    private Date closeDate;
    
    @NotBlank
    private String password;
    
    private float balance;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Movement> movements;
    
    @NotNull
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
    
    /**
     * Validate password
     * 
     * @param password
     * @return 
     */
    private boolean validatePassword(String password) {      
        return !password.isBlank() && password.length() == 8;
    }
    
    /**
     * If the account is closed
     * 
     * @return 
     */
    public boolean isClosed(){
        return this.closeDate != null;
    }
    
    /**
     * Open Account
     * 
     * @param number
     * @param password
     * @param person 
     */
    public void open(Long number,String password,Person person) {
        if(this.validatePassword(password)){
            this.openDate = new Date();
            this.person = person;
            this.number = number;
            this.password = this.encrypt(password);
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }
    
    /**
     * Close Account
     * 
     * @param password
     * @return 
     */
    public boolean close(String password) {
        if(this.checkPassword(password)){
            this.closeDate = closeDate;
        }
        return this.closeDate != null;
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
     * Add movement
     * 
     * @param movement
     * @return 
     */
    public boolean addMovements(String password, Movement movement) {
        if(this.checkPassword(password)){
            if(this.movements == null){
                this.movements = new ArrayList<Movement>();
            }
            movement.setAccount(this);

            if(this.movements.add(movement)){
                this.calculateTheBalance(movement);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Calculate the balance
     * 
     * @param movement 
     */
    private void calculateTheBalance(Movement movement){
        switch(movement.getType()){
            case CREDIT:
                this.balance = this.balance + movement.getValue();
               break;
            case DEBIT:
                this.balance = this.balance - movement.getValue();
                break;
        }
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
