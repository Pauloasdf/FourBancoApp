/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.model.Movement;
import com.bank.acelera.repository.MovementRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lauro
 */
@Service
public class MovementService {
    
    @Autowired
    MovementRepository movementRepository;
    
    /**
     * Add movement
     * 
     * @param movement
     * @return 
     */
    public Movement save(Movement movement) {
        if(movement.getAccount() == null){
            throw new IllegalArgumentException("Account cannot be null");
        }
        
        return movementRepository.save(movement);
    }
    
    public List<Movement> findBetween(Long numberAccount, Date start, Date end){
        List<Movement> movements = movementRepository.findByDateBetween(start, end);
        
        return movements;
    }
}
