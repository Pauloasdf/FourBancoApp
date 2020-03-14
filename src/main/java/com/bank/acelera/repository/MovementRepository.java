
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.repository;

import com.bank.acelera.model.Movement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lauro
 */
@Repository
public interface MovementRepository  extends JpaRepository<Movement, Integer> {
    
    public List<Movement> findByAccountNumberAndDateBetween(Long accountNumber, Date start, Date end);

    Optional<List<Movement>> findByAccountNumber(Long accountNumber);
}
