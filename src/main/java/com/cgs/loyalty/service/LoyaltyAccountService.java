package com.cgs.loyalty.service;

import com.cgs.loyalty.dto.AccountDto;

public interface LoyaltyAccountService {

	// Create Account
	public AccountDto saveAccount(AccountDto accountDto);

	// delete account
	public void deleteByAccountNo(String accountNo);

	// get availabel points
	public String checkPoints(String accountNumber);
	
	// get account balance
	public String checkBalance(String accountNumber);

	// get account by id 
	public AccountDto getAccountById(String accountId);



	

}
