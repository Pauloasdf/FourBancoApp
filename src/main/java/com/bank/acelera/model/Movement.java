/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author lauro
 */
@Entity
public class Movement {
    
    
    public enum Type { CANCELED , CREDIT , DEBIT };
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private Date date;
    
    private float value;
   
    private Type type;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    public Movement() {
    }
   
    public Movement(float value, Type type) {
        this.value = value;
        this.type = type;
    }
    
    public int getId() {
        return id;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public Type getType() {
        return type;
    }

    public float getValue() {
        return value;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
