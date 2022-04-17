package com.banking.account.service.app.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.banking.account.service.app.entity.Customer;

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
	//This define if the customer is a natural person or a legal person
	//Define si el cliente es una persona natural o una persona jurídica
	private boolean legalAccount = false;
	//In case of legalCustomer was true, this attribute shouldn't be empty
	//En caso de que el atributo legalCustomer fuese verdadero, este atributo no deberia estar vacio
	private int tributaryIdentifier;
	//Number account
	private Long accountNumber;
	//Dollar or soles
	private int moneyType;
	// verification pin
	private String password;
	//encrypted pin
	private String hashedpassword;
	@Transient
	//Account owners, titled
	private List<Customer> owners;
	private List<Customer> authorities;
	
	//Maximum amount
	private Double maxAuthAmount;
	//Amount left
	private Date amountLeftReload;
	
	//Type account
	private int productType;
	//Dependency injection
	private Restriction restrictions;
	//Temporary promotion
	private Boolean offer = false;
	//Number monthly movements
	private int monthMoves;
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
