/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bank.acelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * @author lauro
 */
@NoRepositoryBean
public interface PersonBaseRepository<T>  extends JpaRepository<T, Integer> {
    
    public T findByName(String name);
}