/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.repository.person;

import com.bank.acelera.model.abstrac.Person;
import javax.transaction.Transactional;

/**
 *
 * @author lauro
 */
@Transactional
public interface PersonRepository extends PersonBaseRepository<Person>{
    
}
