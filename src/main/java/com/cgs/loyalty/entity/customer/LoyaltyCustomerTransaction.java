package com.cgs.loyalty.entity.customer;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "customer_transaction")
public class LoyaltyCustomerTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long transaction_id;
	private String account_id;
	private String beneficiary;
	private String beneficiary_acc_no;
	private String debit_amount;
	private String credi_amount;
	private String totalAmount;
	private String pointsFor_credit;
	private String totalPoints;
	private LocalDateTime created_at;

}
