package com.cgs.loyalty.service;

import com.cgs.loyalty.util.CreditAmount;
import com.cgs.loyalty.util.DebitAmount;
import com.cgs.loyalty.util.UsePoints;

public interface LoyaltyTransactionService {

	// deposit amount
	void doDeposit(CreditAmount credi_amount);

	// debit amount
	void doDebit(DebitAmount debitAmount);

	// use points
	void usePoints(UsePoints usePoints);

	// convert points to money
	String usePointstoConvert(UsePoints usePoints);
	
	
}
