/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.repository;

import com.bank.acelera.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lauro
 */
@Repository
public interface PersonRepository  extends JpaRepository<Person, Integer> {
    
    public Person findByName(String name);
}
