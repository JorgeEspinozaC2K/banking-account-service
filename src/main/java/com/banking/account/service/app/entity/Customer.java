package com.banking.account.service.app.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Customer {

	private String id;
	private String name;
	private String lastName;
	private int personalIdentifier;
	private String email;
	private Date createAt = new Date();
	private Date updateAt;

}