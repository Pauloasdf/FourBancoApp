/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.repository.person;

import com.bank.acelera.model.abstrac.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author lauro
 */
@NoRepositoryBean
public interface PersonBaseRepository<T extends Person>  extends JpaRepository<T, Long> {
    
    public T findByName(String name);
}