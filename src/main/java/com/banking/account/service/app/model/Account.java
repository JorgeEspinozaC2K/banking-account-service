package com.banking.account.service.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {
	
	//Is needed an attribute customer of type Customer
	
	@Id
	private String id;
	//Number of account
	private Long accountNumber;
	// Name of the client or company
	private String name;
	//Business or personal account
	private Boolean businessType;
	//Identification number of the person or company 
	private int indentification;
	//Bank account or credit
	private String product;
	//Type of bank account
	private String producType;
	//Savings bank account
	private String saving;
	//Bank current account
	private String currentAccount;
	//Fixed term account
	private String fixedTerm;
}
