package com.cgs.loyalty.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AccountDto {

	private String accountId;
	private String customerId;
	private String account_number;
	private String account_type;
	private String accoutBalance="0";
	private String totalPoints="0";
	private LocalDateTime created_at = LocalDateTime.now();
	
	
}
