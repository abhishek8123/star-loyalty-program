package com.cgs.loyalty.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgs.loyalty.exception.CustomerNotExistException;
import com.cgs.loyalty.repository.LoyaltyAccountRepository;
import com.cgs.loyalty.repository.ProcedureRepository;
import com.cgs.loyalty.service.LoyaltyTransactionService;
import com.cgs.loyalty.util.CreditAmount;
import com.cgs.loyalty.util.DebitAmount;
import com.cgs.loyalty.util.TransactionValidation;
import com.cgs.loyalty.util.UsePoints;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoyaltyTransactionServiceImpl implements LoyaltyTransactionService {

	@Autowired
	private ProcedureRepository procedureRepository;

	@Autowired
	private TransactionValidation transactionValidation;

	@Autowired
	private LoyaltyAccountRepository loyaltyAccountRepository;

	// deposit amount
	@Override
	public void doDeposit(CreditAmount credi_amount) {
		log.info("In deposit ammount layalty customer transaction service");

		boolean value = transactionValidation.validateCredit(credi_amount);
		if (value == true) {
			procedureRepository.creditAmount(credi_amount);
		}
	}

	// debit amount
	@Override
	public void doDebit(DebitAmount debitAmount) {
		log.info("In debit ammount layalty customer transaction service");
		boolean value = transactionValidation.validateDebit(debitAmount);
		if (value == true) {
			procedureRepository.debitAmount(debitAmount);
		}
	}

	// use your points
	@Override
	public void usePoints(UsePoints usePoints) {
		log.info("In use points layalty customer transaction service");
		String acountId = loyaltyAccountRepository.findAccountIdByAccountId(usePoints.getAccountId());
		if (acountId.equals(usePoints.getAccountId())) {
			boolean value = transactionValidation.validatePoints(usePoints);
			if (value == true) {
				procedureRepository.usePoints(usePoints);
			} else {
				throw new CustomerNotExistException();
			}
		}
	}
	
	// use your points and convert into money
	@Override
	public String usePointstoConvert(UsePoints usePoints) {
		log.info("In use points layalty customer transaction service");
		String acountId = loyaltyAccountRepository.findAccountIdByAccountId(usePoints.getAccountId());
		
		if (acountId.equals(usePoints.getAccountId())) {
			boolean value = transactionValidation.validatePoints(usePoints);
			if (value == true) {
				String money = procedureRepository.usePointstoMoney(usePoints);
				return money;
			} else {
				throw new NullPointerException();
			}
		}
		 throw new CustomerNotExistException();
	}
	
	
}
