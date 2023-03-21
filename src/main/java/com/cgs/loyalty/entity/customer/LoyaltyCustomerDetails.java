package com.cgs.loyalty.entity.customer;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customer_Loyalty")
public class LoyaltyCustomerDetails {

	@Id
	private String customerId;
	private String name;
	private String mobileNo;
	private String email;
	private String dob;
	private String customerType;
	private String rating;
	private String channelOfRegistration;
	
}
