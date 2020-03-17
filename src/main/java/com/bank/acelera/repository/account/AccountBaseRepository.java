/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.repository.account;

import com.bank.acelera.model.abstrac.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author lauro
 */
@NoRepositoryBean
public interface AccountBaseRepository<T extends Account>  extends JpaRepository<T, Integer> {
    
    Optional<T> findByNumber(long number);
}
