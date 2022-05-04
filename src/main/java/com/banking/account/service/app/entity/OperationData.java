package com.banking.account.service.app.entity;

import lombok.Data;

@Data
public class OperationData {
	
	private String accountId;
	private String customerId; 
	private Double accountChange; 
	private String destinyAccountId; 
	private String destinyCustomerId;
	
}
