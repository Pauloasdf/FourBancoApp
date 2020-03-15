/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.Legal;
import com.bank.acelera.model.Physical;
import com.bank.acelera.model.abstrac.Person;
import com.bank.acelera.repository.PersonBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
class PersonService {

    @Autowired
    private PersonBaseRepository<Physical> physicalRepository;
    
    @Autowired
    private PersonBaseRepository<Legal> legalRepository;
    
    public Person findById(Long personId) {
        return physicalRepository.findById(personId).get();
    }
    
}
