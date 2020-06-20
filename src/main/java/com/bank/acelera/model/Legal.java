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
public class Legal extends Person {
    
    private String cnpj;

    public Legal() {}
    
    public Legal(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }
    
    public String getCnpj() {
        return cnpj;
    }
}
