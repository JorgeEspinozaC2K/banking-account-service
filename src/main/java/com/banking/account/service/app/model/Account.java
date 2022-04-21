package com.banking.account.service.app.model;

import java.util.Date;
import java.util.List;

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

	//Number account
	private Long accountNumber;
	
	//Account owners, titled
	private List<String> owners;
	private List<String> authorities;
	
	//Type account
	private Integer productType;
	//Dependency injection
	private Restriction restrictions;
	//Temporary promotion
	private Boolean offer = false;
	//Number monthly movements
	private Integer monthMoves;
	//Record withdrawal date 
	private Date witdrawalDay;
	//Record deposit date
	private Date depositDay;
	//Current Balance
	private Double amountLeft = 0.00;
	//Date modify
	private Date modify;
	//Date of creation
	private Date createAt;
}
