package com.cgs.loyalty.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TransactionDto {
	
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
