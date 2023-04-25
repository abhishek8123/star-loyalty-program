package com.cgs.loyalty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cgs.loyalty.advice.ErrorDetails;
import com.cgs.loyalty.dto.AccountDto;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerAccount;
import com.cgs.loyalty.exception.IdAlreadyExistException;
import com.cgs.loyalty.repository.LoyaltyAccountRepository;

@Component
public class AccountValidator {

	@Autowired
	LoyaltyAccountRepository loyaltyAccountRepository;

//	Validate customer parameters
	public List<ErrorDetails> validateToCreateAccountRequest(AccountDto accountDto) {

		Optional<LoyaltyCustomerAccount> account = loyaltyAccountRepository.findById(accountDto.getAccountId());

		if (account.isEmpty()) {

			String regaxAccoId = "^[\\d]{6}$";
			String regaxCustId = "^[\\d]{4}$";
			String regaxAccNo = "^[\\d]{8}$";

			boolean accId = accountDto.getAccountId().matches(regaxAccoId);
			boolean custId = accountDto.getCustomerId().matches(regaxCustId);
			boolean accNo = accountDto.getAccount_number().matches(regaxAccNo);

			List<ErrorDetails> errors = new ArrayList<>();

			// Account Id
			if (accId == false) {
				ErrorDetails error = new ErrorDetails("AccountId", "Account id should have only 6 digits !!");
				errors.add(error);
			}

			// Customer Id
			if (custId == false) {
				ErrorDetails error = new ErrorDetails("CustomerId", "customer id should have only 4 digits !!");
				errors.add(error);
			}

			// Account No
			if (accNo == false) {
				ErrorDetails error = new ErrorDetails("AccountNo", "Account no should have only 8 digits !!");
				errors.add(error);
			}
			
			// account Type
			if (accountDto.getAccount_type().isEmpty() || accountDto.getAccount_type().length() == 0) {
				ErrorDetails error = new ErrorDetails("AccountTye", "account type should not bo empty !!");
				errors.add(error);
			}

			return errors;

		} else {
			throw new IdAlreadyExistException();
		}
	}

////	Validate the List is empty or not
//	public List<LoyaltyCustomerDetails> checkListEmpty() {
//
//		List<LoyaltyCustomerDetails> customers = loyaltyCustomerRepository.findAll();
//		if (customers.isEmpty()) {
//			throw new ListIsEmptyException();
//		}
//		return customers;
//	}
//
////	validate the customerId present in data base or not
//	public LoyaltyCustomerDetails checkCustomerPresentOrNot(String customerId) {
//
//		LoyaltyCustomerDetails customer = loyaltyCustomerRepository.findById(customerId).orElse(null);
//		if (customer == null) {
//			throw new CustomerNotExistException();
//		}
//		return customer;
//	}
//
////	validate the customer present in data base or not
//	public LoyaltyCustomerDetails checkCustomerPresentOrNot(CustomerDto customerDto) {
//
//		LoyaltyCustomerDetails customer = loyaltyCustomerRepository.findById(customerDto.getCustomerId()).orElse(null);
//		if (customer == null) {
//			throw new CustomerNotExistException();
//		}
//		
//		return customer;
//	}

}
