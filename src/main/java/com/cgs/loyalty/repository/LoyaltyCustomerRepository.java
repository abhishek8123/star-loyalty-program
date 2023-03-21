package com.cgs.loyalty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;

@Repository
public interface LoyaltyCustomerRepository extends JpaRepository<LoyaltyCustomerDetails, String> {

    @Query(value = "SELECT customer_id FROM customer_loyalty WHERE customer_id = :accountId",nativeQuery = true )
	String findCustomerById(@Param("accountId") String accountId);


}
