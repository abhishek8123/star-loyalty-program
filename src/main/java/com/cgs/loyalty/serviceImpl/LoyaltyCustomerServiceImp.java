package com.cgs.loyalty.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cgs.loyalty.advice.ErrorDetails;
import com.cgs.loyalty.dto.CustomerDto;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;
import com.cgs.loyalty.exception.BadRequestException;
import com.cgs.loyalty.exception.IdAlreadyExistException;
import com.cgs.loyalty.repository.LoyaltyCustomerRepository;
import com.cgs.loyalty.service.LoyaltyCustomerService;
import com.cgs.loyalty.util.CustomerValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoyaltyCustomerServiceImp implements LoyaltyCustomerService {

	@Autowired
	private LoyaltyCustomerRepository customerLoyaltyRepository;

	@Autowired
	private CustomerValidator customerValidator;

	@Autowired
	private ModelMapper modelMapper;

	// Save customer
	@Override
	public CustomerDto save(CustomerDto customerDto) {

		log.info("In save loyalty customer service");
		
		if(customerValidator.checkIdExist(customerDto.getCustomerId())) {
			throw new IdAlreadyExistException();
		}
		List<ErrorDetails> errors = customerValidator.validateToCreateCustomerRequest(customerDto);
		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}
		LoyaltyCustomerDetails customer = dtoToLoyCustomerDetails(customerDto);
		LoyaltyCustomerDetails savedCustomer = customerLoyaltyRepository.save(customer);
		return loyCustomerDetailsToDto(savedCustomer);
	}

	// get all customer
	@Override
	public List<CustomerDto> getAllCustomer() {
		log.info("In get all loyalty customers service");

		List<LoyaltyCustomerDetails> customers = customerValidator.checkListEmpty();
		List<CustomerDto> dtoCustomers = customers.stream().map(customer -> loyCustomerDetailsToDto(customer))
				.collect(Collectors.toList());
		return dtoCustomers;
	}

	// get customer
	@Override
	public CustomerDto getCustomer(String customerId) {
		log.info("In get loyalty customer by id service");

		LoyaltyCustomerDetails customer = customerValidator.checkCustomerPresentOrNot(customerId);
		return loyCustomerDetailsToDto(customer);
	}

	// Update customer
	@Override
	public CustomerDto addCustomer(CustomerDto customerDto) {
		log.info("In update loyalty customer service");

		LoyaltyCustomerDetails customer = customerValidator.checkCustomerPresentOrNot(customerDto);
		
		List<ErrorDetails> errors = customerValidator.validateToCreateCustomerRequest(customerDto);

		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}

		customer.setName(customerDto.getName());
		customer.setMobileNo(customerDto.getMobileNo());
		customer.setEmail(customerDto.getEmail());
		customer.setDob(customerDto.getDob());
		customer.setCustomerType(customerDto.getCustomerType());
		customer.setRating(customerDto.getRating());
		customer.setChannelOfRegistration(customerDto.getChannelOfRegistration());
		
		LoyaltyCustomerDetails customer1 = customerLoyaltyRepository.save(customer);
		return loyCustomerDetailsToDto(customer1);
	}

	// delete customer
	@Override
	public void deleteById(String customerId) {
		log.info("In delete loyalty customer service");

		LoyaltyCustomerDetails customer = customerValidator.checkCustomerPresentOrNot(customerId);
		customerLoyaltyRepository.deleteById(customer.getCustomerId());

	}

	// ------------------------------------------------------------------------------------//

	// DTO to LoyaltyCustomerDetails
	public LoyaltyCustomerDetails dtoToLoyCustomerDetails(CustomerDto customerDto) {

		LoyaltyCustomerDetails customerDetails = this.modelMapper.map(customerDto, LoyaltyCustomerDetails.class);
		return customerDetails;
	}

	// LoyaltyCustomerDetails to DTO
	public CustomerDto loyCustomerDetailsToDto(LoyaltyCustomerDetails loyCustomerDetails) {

		CustomerDto customerDto = this.modelMapper.map(loyCustomerDetails, CustomerDto.class);
		return customerDto;
	}

	// --------------------------------------------------------------------------------------//

}
