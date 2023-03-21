package com.cgs.loyalty.serviceImpl;

import java.text.DecimalFormat;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgs.loyalty.advice.ErrorDetails;
import com.cgs.loyalty.dto.AccountDto;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerAccount;
import com.cgs.loyalty.exception.BadRequestException;
import com.cgs.loyalty.exception.CustomerNotExistException;
import com.cgs.loyalty.repository.LoyaltyAccountRepository;
import com.cgs.loyalty.repository.LoyaltyCustomerRepository;
import com.cgs.loyalty.service.LoyaltyAccountService;
import com.cgs.loyalty.util.AccountValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoyaltyAccountServiceImpl implements LoyaltyAccountService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LoyaltyAccountRepository loyaltyAccountRepository;

	@Autowired
	private LoyaltyCustomerRepository loyaltyCustomerRepository;

	@Autowired
	private AccountValidator accountValidator;

	public static final DecimalFormat df = new DecimalFormat("0.00");

	// save Account
	@Override
	public AccountDto saveAccount(AccountDto accountDto) {
		log.info("In save layalty customer account service");

		String customerId = loyaltyCustomerRepository.findCustomerById(accountDto.getCustomerId());
		if (customerId != null) {
			List<ErrorDetails> errors = accountValidator.validateToCreateAccountRequest(accountDto);
			if (errors.size() > 0) {
				throw new BadRequestException(errors);
			}
			LoyaltyCustomerAccount account = dtoToLoyCustomerAccount(accountDto);
			return loyCustomerAccountToDto(loyaltyAccountRepository.save(account));
		} else {
			throw new CustomerNotExistException();
		}
	}

	// check account balance
	@Override
	public String checkBalance(String accountNumber) {
		log.info("In check account balance of layalty customer account service");

		String acountNo = loyaltyAccountRepository.findAccountNoByAccountNo(accountNumber);
		if (acountNo != null) {
			String balance = loyaltyAccountRepository.findBalanceByAccountNo(accountNumber);
			return balance;
		} else {
			throw new CustomerNotExistException();
		}
	}

	// Check availabel points
	@Override
	public String checkPoints(String accountNumber) {
		log.info("In check availbel points of layalty customer account service");

		String acountNo = loyaltyAccountRepository.findAccountNoByAccountNo(accountNumber);
		if (acountNo != null) {
			String points = loyaltyAccountRepository.findPointsByAccountNo(accountNumber);
			return points;
		} else {
			throw new CustomerNotExistException();
		}
	}

	// delete account by id
	@Override
	public void deleteByAccountNo(String accountNo) {
		log.info("In save layalty customer account service");

		String acountNo = loyaltyAccountRepository.findAccountNoByAccountNo(accountNo);
		if (acountNo != null) {
			loyaltyAccountRepository.deleteById(accountNo);

		} else {
			throw new CustomerNotExistException();
		}

	}

	// get account by account no
	@Override
	public AccountDto getAccountById(String accountNumber) {
		log.info("In save layalty customer account service");
		
		String acountNo = loyaltyAccountRepository.findAccountNoByAccountNo(accountNumber);
		if (acountNo != null) {
			LoyaltyCustomerAccount account = loyaltyAccountRepository.findByAccountNo(accountNumber);
			return loyCustomerAccountToDto(account);
		} else {
			throw new CustomerNotExistException();
		}
	}

	// ------------------------------------------------------------------------------------//

	// DTO to LoyaltyCustomerAccount
	public LoyaltyCustomerAccount dtoToLoyCustomerAccount(AccountDto accountDto) {

		LoyaltyCustomerAccount accountDetails = this.modelMapper.map(accountDto, LoyaltyCustomerAccount.class);
		return accountDetails;
	}

	// LoyaltyCustomerAccount to DTO
	public AccountDto loyCustomerAccountToDto(LoyaltyCustomerAccount loyCustomerAccount) {

		AccountDto accountDto = this.modelMapper.map(loyCustomerAccount, AccountDto.class);
		return accountDto;
	}

	// ------------------------------------------------------------------------------------//

}
