/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author lauro
 */
@Entity
public class AccountNumber {
    
    @Id
    private int id;
    
    private Long seguence;

    public Long getSeguence() {
        return seguence;
    }

    public void setSeguence(Long seguence) {
        this.seguence = seguence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void incremente() {
        this.seguence++;
    }
    
}
