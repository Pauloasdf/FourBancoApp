/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.model;

import com.bank.acelera.model.abstrac.Person;
import javax.persistence.Entity;

/**
 *
 * @author lauro
 */
@Entity
public class Physical extends Person {
    
    private String cpf;

    public Physical() {}

    public Physical(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
