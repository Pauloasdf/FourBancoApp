/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.controller.converter.PersonConverter;
import com.bank.acelera.controller.request.PersonRequest;
import com.bank.acelera.model.Legal;
import com.bank.acelera.model.Physical;
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

    public boolean create(PersonRequest personRequest) throws IllegalArgumentException{
        Person person = personConverter.converter(personRequest);
        if(person instanceof Physical) {
            return this.create((Physical) person);
        }
        
        if(person instanceof Legal){
            return this.create((Legal) person);
        }
        return false;
    }

    private boolean create(Legal person) {
        if(person.getCnpj() == null || person.getCnpj().isBlank()){
            throw new IllegalArgumentException(Legal.CNPJ_EMPTY);
        }
        return personRepository.save(person) != null;
    }

    private boolean create(Physical person) {
        if(person.getCpf() == null || person.getCpf().isBlank()){
            throw new IllegalArgumentException(Physical.CPF_EMPTY);
        }
        return personRepository.save(person) != null;
    }
}
