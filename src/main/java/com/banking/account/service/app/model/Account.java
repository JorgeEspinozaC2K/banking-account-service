package com.banking.account.service.app.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.banking.account.service.app.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	private List<Customer> owners;
	private List<Customer> authorities;
	
	//Type account
	private Integer productType;
	//Dependency injection
	private Restriction restrictions;
	//Temporary promotion
	private Boolean offer = false;
	//Number monthly movements
	private Integer monthMoves;
	
	//Record withdrawal date
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate witdrawalDay;
	
	//Record deposit date
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate depositDay;
	
	//Current Balance
	private Double amountLeft = 0.00;
	
	//Date modify
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate modify;
	
	//Date of creation
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createAt;
}
