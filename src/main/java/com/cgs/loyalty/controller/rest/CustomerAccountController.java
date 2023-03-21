package com.cgs.loyalty.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cgs.loyalty.dto.AccountDto;
import com.cgs.loyalty.service.LoyaltyAccountService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/account")
public class CustomerAccountController {

	@Autowired
	private LoyaltyAccountService loyaltyAccountService;
	
    // save account
	@PostMapping("/saveAccount")
	public ResponseEntity<String> saveAccount(@RequestBody AccountDto accountDto) {
		log.info("In save layalty customer account controller");
		loyaltyAccountService.saveAccount(accountDto);
		String account_created = "account is created for " + accountDto.getCustomerId() + " this customer id";
		return new ResponseEntity<String>(account_created, HttpStatus.CREATED);
	}

	// delete account
	// @DeleteMapping("/delete/{accountNo}")
	public ResponseEntity<String> deleteAccount(@PathVariable("accountNo") String accountNo){
		log.info("In delete layalty customer account controller");
		loyaltyAccountService.deleteByAccountNo(accountNo);
		String msg = accountNo+" This account No is Deleted";
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	// get account details
	@GetMapping("/getAccount/{accountNumber}")
	public ResponseEntity<AccountDto> getAccountByIDd(@PathVariable("accountNumber") String accountNumber){
		log.info("in get layalty customer account by id controller");
		AccountDto account = loyaltyAccountService.getAccountById(accountNumber);
		return new ResponseEntity<AccountDto>(account,HttpStatus.OK);
	}

	// get availabel points
	@GetMapping("/checkPoints/{accountNumber}")
	public ResponseEntity<String> checkAvailabelPoints(@PathVariable("accountNumber") String accountNumber) {
		log.info("in get availabel points of loyalty customer controller");
		String points = loyaltyAccountService.checkPoints(accountNumber);
		String msg = "Available points in Your account : " + points +" points";
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	// Check account balance
	@GetMapping("/checkBalance/{accountNumber}")
	public ResponseEntity<String> checkAccountBalance(@PathVariable("accountNumber") String accountNumber) {
		log.info("in customer loyalty account balance controller");
		String amount = loyaltyAccountService.checkBalance(accountNumber);
		String msg = "Your account balance : " + amount +" Rs";
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
}
