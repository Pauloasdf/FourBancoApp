/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.abstrac.Person;
import com.bank.acelera.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
class PersonService {

    @Autowired
    private PersonRepository personRepository;
        
    public Person findById(Long personId) {
        return personRepository.findById(personId).get();
    }
    
}
