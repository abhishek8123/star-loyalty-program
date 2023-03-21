package com.cgs.loyalty.util;

import lombok.Data;

@Data
public class DebitAmount {
	
	private String accountId;
	private String debitAmount;
	private String beneficiary;
	private String beneficiary_acc_no;

}
