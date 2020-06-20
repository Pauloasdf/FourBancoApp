/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.controller.converter.PersonConverter;
import com.bank.acelera.controller.request.PersonRequest;
import com.bank.acelera.model.abstrac.Person;
import com.bank.acelera.repository.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private PersonConverter personConverter;
        
    public Person findById(Long personId) {
        return personRepository.findById(personId).get();
    }

    public boolean create(PersonRequest personRequest) {
        Person person = personConverter.converter(personRequest);
        return this.create(person);
    }

    private boolean create(Person person) {
        return personRepository.save(person) != null;
    }
    
}
