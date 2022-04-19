package com.banking.account.service.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.service.app.model.Account;
import com.banking.account.service.app.service.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/account")

public class AccountController {

	
	//Dependency injection required for the class work
	@Autowired
	private AccountService accountService;
	
	
	@GetMapping
	//This method displays all the information from the account database
	public Flux<Account> index() {
		return accountService.findAll();
	}
	
	//This method save or update a record from the account database
	@PostMapping("/save")
	public Mono<Account> save(@RequestBody Account account)	{
		return accountService.save(account);
	}

}
