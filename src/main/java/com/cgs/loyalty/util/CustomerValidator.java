package com.cgs.loyalty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cgs.loyalty.advice.ErrorDetails;
import com.cgs.loyalty.dto.CustomerDto;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;
import com.cgs.loyalty.exception.CustomerNotExistException;
import com.cgs.loyalty.exception.IdAlreadyExistException;
import com.cgs.loyalty.exception.ListIsEmptyException;
import com.cgs.loyalty.repository.LoyaltyCustomerRepository;

@Component
public class CustomerValidator {

	@Autowired
	LoyaltyCustomerRepository loyaltyCustomerRepository;

//	Validate customer parameters
	public List<ErrorDetails> validateToCreateCustomerRequest(CustomerDto customerDto) {

		Optional<LoyaltyCustomerDetails> customer = loyaltyCustomerRepository.findById(customerDto.getCustomerId());

		if (customer.isEmpty()) {

			String regaxId = "^[\\d]{4}$";
			String regaxEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
			String regaxDob = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
			String regaxRating = "^[1-5]$";
			boolean id = customerDto.getCustomerId().matches(regaxId);
			boolean email = customerDto.getEmail().matches(regaxEmail);
			boolean dob = customerDto.getDob().matches(regaxDob);
			boolean rating = customerDto.getRating().matches(regaxRating);

			List<ErrorDetails> errors = new ArrayList<>();

			// Id
			if (id == false) {
				ErrorDetails error = new ErrorDetails("Id", "Customer id should have only 4 digits !!");
				errors.add(error);
			}

			// Name
			if (customerDto.getName().isEmpty() || customerDto.getName().length() == 0) {
				ErrorDetails error = new ErrorDetails("Name", "Customer name should be greater than 3 charecters !!");
				errors.add(error);
			}

			// MobileNo
			if (customerDto.getMobileNo().length() != 10) {
				ErrorDetails error = new ErrorDetails("MobileNo",
						"Mobile Number should be having the length of 10 numbers !!");
				errors.add(error);
			}

			// Email
			if (email == false) {
				ErrorDetails error = new ErrorDetails("Email", "Email should be in the formate of xyz@gmail.com !!");
				errors.add(error);
			}

			// DateOfBirth
			if (dob == false) {
				ErrorDetails error = new ErrorDetails("Date of Birth",
						"Date of Birth should be in the formate of dd/mm/yyyy !!");
				errors.add(error);
			}

			// CustomerType
			if (customerDto.getCustomerType().isEmpty() || customerDto.getCustomerType().length() == 0) {
				ErrorDetails error = new ErrorDetails("CustomerType", "customer type should not be empty !!");
				errors.add(error);
			}

			// Rating
			if (rating == false) {
				ErrorDetails error = new ErrorDetails("Rating", "Rating should be in the rang of 1-5 !!");
				errors.add(error);
			}

			// ChannelOfRegistration
			if (customerDto.getChannelOfRegistration().isEmpty()
					|| customerDto.getChannelOfRegistration().length() == 0) {
				ErrorDetails error = new ErrorDetails("ChannelOfRegistration",
						"customer ChannelOfRegistration should not be empty");
				errors.add(error);
			}

			return errors;

		} else {
			throw new IdAlreadyExistException();
		}
	}

//	Validate the List is empty or not
    public List<LoyaltyCustomerDetails> checkListEmpty() {

        List<LoyaltyCustomerDetails> customers = loyaltyCustomerRepository.findAll();
        if (customers.isEmpty()) {
            throw new ListIsEmptyException();
        }
        return customers;
    }

//	validate the customerId present in data base or not
    public LoyaltyCustomerDetails checkCustomerPresentOrNot(String customerId) {

        LoyaltyCustomerDetails customer = loyaltyCustomerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomerNotExistException();
        }
        return customer;
    }

//	validate the customer present in data base or not
    public LoyaltyCustomerDetails checkCustomerPresentOrNot(CustomerDto customerDto) {

        LoyaltyCustomerDetails customer = loyaltyCustomerRepository.findById(customerDto.getCustomerId()).orElse(null);
        if (customer == null) {
            throw new CustomerNotExistException();
        }
        return customer;
    }
	


}
