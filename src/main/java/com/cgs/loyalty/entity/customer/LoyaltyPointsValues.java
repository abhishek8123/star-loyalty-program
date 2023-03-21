package com.cgs.loyalty.entity.customer;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer_points_value")
public class LoyaltyPointsValues {
	
	@Id
	private String id;
	private String name;
	private String bank_name;
	private String points_per_trans;
	private String per_points;
	private String ammount;
	private LocalDateTime created_at;
	
}
