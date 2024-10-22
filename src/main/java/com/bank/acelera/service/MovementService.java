/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.service;

import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.controller.response.MovementResponse;
import com.bank.acelera.model.abstrac.Account;
import com.bank.acelera.model.Movement;
import com.bank.acelera.controller.converter.MovementConverter;
import com.bank.acelera.repository.account.MovementRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    MovementConverter movementConverter;

    @Autowired
    AccountService accountService;

    /**
     * Save movement
     *
     * @param movement
     * @return
     */
    public Movement save(Movement movement) {
        if (movement.getAccount() == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        return movementRepository.save(movement);
    }

    /**
     * Find by account number and between dates
     *
     * @param numberAccount
     * @param start
     * @param end
     * @return
     */
    public List<Movement> findBetween(Long numberAccount, Date start, Date end) {
        List<Movement> movements = movementRepository.findByAccountNumberAndDateBetween(numberAccount, start, end);
        return movements;
    }

    /**
     * Returns movements of an account
     *
     * @param accountNumber
     * @return
     */
    public List<MovementResponse> getMovements(Long accountNumber) {
        Optional<List<Movement>> movementList = movementRepository.findByAccountNumber(accountNumber);
        return movementConverter.converterList(movementList.get());
    }

    /**
     *
     * @param movementRequest
     * @return
     */
    public boolean addMovement(MovementRequest movementRequest) throws IllegalArgumentException {
        Movement movement = movementConverter.movementConverter(movementRequest);
        return this.addMovement(movementRequest.getAccountNumber(), movement);
    }

    /**
     * Add movement
     *
     * @param movement
     * @return
     */
    public boolean addMovement(long number, Movement movement) throws IllegalArgumentException {
        Account account = accountService.findByNumber(number);

        if (accountService.allowedMovement(account, movement)) {

            movement.setDate(new Date());
            movement.setAccount(account);

            try {
                accountService.calculateTheBalance(account, movement);
                this.save(movement);
                accountService.save(account);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
