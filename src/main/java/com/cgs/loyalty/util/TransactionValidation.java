package com.cgs.loyalty.util;

import org.springframework.stereotype.Component;

@Component
public class TransactionValidation {

	public boolean validateCredit(CreditAmount credAmount) {
		
		if (credAmount.getAccountId().isEmpty() || credAmount.getCreditAmount().isEmpty())
			throw new NullPointerException();
		else
			return true;
	}

	public boolean validateDebit(DebitAmount debitAmount) {
		if (debitAmount.getAccountId().isEmpty() || debitAmount.getDebitAmount().isEmpty())
			throw new NullPointerException();
		else
			return true;
	}

	public boolean validatePoints(UsePoints usePoints) {
		if (usePoints.getAccountId().isEmpty() || usePoints.getPoints().isEmpty())
			throw new NullPointerException();
		else
		return true;
	}

}
