package com.cgs.loyalty.service;

import java.util.List;
import com.cgs.loyalty.dto.CustomerDto;

public interface LoyaltyCustomerService {

	// save customer 
	public CustomerDto save(CustomerDto customerDto);

	// Get All Customers
	public List<CustomerDto> getAllCustomer();

	// Get a Customer 
	public CustomerDto getCustomer(String customerId);

	// Update Customer
	public CustomerDto addCustomer(CustomerDto customerDto);

	// Delete Customer
	public void deleteById(String customerId);

	

}
