package com.cgs.loyalty.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cgs.loyalty.dto.CustomerDto;
import com.cgs.loyalty.service.LoyaltyCustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class CustomerRestController {

	@Autowired
	private LoyaltyCustomerService loyaltyCustomerService;

	// Register a customer into Loyalty program
	@PostMapping("/register")
	public ResponseEntity<CustomerDto> registerLoyaltyCustomer(@RequestBody CustomerDto customerDto) {
		log.info("In register loyalty customer controller");
		CustomerDto savedCustomer = loyaltyCustomerService.save(customerDto);
		return new ResponseEntity<CustomerDto>(savedCustomer, HttpStatus.CREATED);
	}

	// Get All Customers by jpa
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDto>> getAllCustomer() {
		log.info("In get all loyalty customers controller");
		List<CustomerDto> savedCustomer = loyaltyCustomerService.getAllCustomer();
		return new ResponseEntity<List<CustomerDto>>(savedCustomer, HttpStatus.FOUND);
	}

	// Get Customer
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") String customerId) {
		log.info("In get loyalty customer by id controller");
		CustomerDto customer = loyaltyCustomerService.getCustomer(customerId);
		return new ResponseEntity<CustomerDto>(customer, HttpStatus.FOUND);
	}

	// Update Customer
	@PutMapping("/update")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
		log.info("In update loyalty customer controller");
		CustomerDto savedCustomer = loyaltyCustomerService.addCustomer(customerDto);
		return new ResponseEntity<CustomerDto>(savedCustomer, HttpStatus.CREATED);
	}

	// Delete Customer
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable("customerId") String customerId) {
		log.info("In delete loyalty customer controller");
		loyaltyCustomerService.deleteById(customerId);
		String masg = customerId + " : Id is deleted";
		return new ResponseEntity<String>(masg, HttpStatus.ACCEPTED);
	}

}
