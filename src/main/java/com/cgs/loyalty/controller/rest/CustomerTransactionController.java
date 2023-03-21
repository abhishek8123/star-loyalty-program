package com.cgs.loyalty.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cgs.loyalty.serviceImpl.LoyaltyTransactionServiceImpl;
import com.cgs.loyalty.util.CreditAmount;
import com.cgs.loyalty.util.DebitAmount;
import com.cgs.loyalty.util.UsePoints;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/transaction")
@Slf4j
public class CustomerTransactionController {

	@Autowired
	private LoyaltyTransactionServiceImpl transactionService;

	// crediting amount from your account
	@PostMapping("/deposit")
	public ResponseEntity<?> doDeposit(@RequestBody CreditAmount credi_amount) {
		log.info("In deposit amount layalty customer transaction controller");

		transactionService.doDeposit(credi_amount);
		return new ResponseEntity<String>("credited Ammount : " + credi_amount.getCreditAmount(), HttpStatus.OK);
	}
   
	// debiting amount from your account
	@PostMapping("/debit")
	public ResponseEntity<String> doDebit(@RequestBody DebitAmount debitAmount) {
		log.info("In debit amount layalty customer transaction controller");

		transactionService.doDebit(debitAmount);
		return new ResponseEntity<String>("debited Ammount : " + debitAmount.getDebitAmount(), HttpStatus.OK);
	}
   
	//use points to purchase or send to any one
	@PostMapping("/usePoints")
	public ResponseEntity<String> usePoints(@RequestBody UsePoints usePoints) {
		log.info("In use points layalty customer transaction controller");

		transactionService.usePoints(usePoints);
		String msg = "you used these much of points : " + usePoints.getPoints();
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	// convert your point into money
	@PostMapping("/pointsToMoney")
	public ResponseEntity<?> convertPointsToMoney(@RequestBody UsePoints usePoints){
		log.info("In use points layalty customer transaction controller");
        
		String money = transactionService.usePointstoConvert(usePoints);
		String msg = "you used "+usePoints.getPoints()+ " and you got "+money+" Rs";
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}

}
