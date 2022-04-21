package com.banking.account.service.app.model;

import java.util.Date;
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
	//Dollar or soles
	private Integer moneyType;
	// verification pin
	private String password;
	//encrypted pin
	private String hashedpassword;
	
	//Account owners, titled
	private String owners;
	private String authorities;
	
	//Maximum amount
	private Double maxAuthAmount;
	//Amount left
	private Date amountLeftReload;
	
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
