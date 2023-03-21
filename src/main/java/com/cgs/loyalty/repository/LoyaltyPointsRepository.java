package com.cgs.loyalty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgs.loyalty.entity.customer.LoyaltyPointsValues;

     @Repository
     public interface LoyaltyPointsRepository extends JpaRepository<LoyaltyPointsValues, String> {

    }
