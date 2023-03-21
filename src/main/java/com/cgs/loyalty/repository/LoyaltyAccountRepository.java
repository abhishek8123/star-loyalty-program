package com.cgs.loyalty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerAccount;

@Repository
public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyCustomerAccount, String> {


	@Query(value = "SELECT accout_balance FROM customer_account WHERE account_number = :accountNo ",nativeQuery = true)
	String findBalanceByAccountNo(@Param("accountNo")String accountNo);

	@Query(value = "SELECT total_points FROM customer_account WHERE account_number = :accountNo ",nativeQuery = true)
	String findPointsByAccountNo(@Param("accountNo") String accountNo);
	
	@Query(value = "SELECT * FROM customer_account WHERE account_number = :accountNumber ",nativeQuery = true)
	LoyaltyCustomerAccount findByAccountNo(@Param("accountNumber") String accountNumber);
	
	@Query(value = "SELECT ACCOUNT_NUMBER FROM customer_account WHERE account_number = :accountNo ",nativeQuery = true)
	String findAccountNoByAccountNo(@Param("accountNo") String accountNo);

	@Query(value = "SELECT ACCOUNT_ID FROM customer_account WHERE account_id = :accountId ",nativeQuery = true)
	String findAccountIdByAccountId(@Param("accountId") String accountId);


	



    

}
