package com.banking.account.service.app.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.service.app.entity.OperationData;
import com.banking.account.service.app.model.Account;
import com.banking.account.service.app.service.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account")

public class AccountController {

	
	//Dependency injection required for the class work
	@Autowired
	private AccountService accountService;
	
	
	@GetMapping
	//This method displays all the information from the account database
	public Flux<Account> index() {
		return accountService.findAll();
	}
	@GetMapping("/{id}")
	public Mono<Account> findtById(@PathVariable String id){
		return accountService.findById(id);
	}
	
	@GetMapping("/amount/{id}")
	public Mono<Double> findtAmount(@PathVariable String id){
		return accountService.amountConsult(id);
	}
	
	@PostMapping("/operation/witdraw")
	public Mono<Double> operationWitdraw(OperationData operationData){
		return accountService.amountUpdate(
				operationData.getAccountId(),
				operationData.getCustomerId(),
				operationData.getAccountChange(), 
				0,
				null,
				null);
	}
	
	@PostMapping("/operation/deposit")
	public Mono<Double> operationDeposit(OperationData operationData){
		return accountService.amountUpdate(
				operationData.getAccountId(),
				operationData.getCustomerId(),
				operationData.getAccountChange(), 
				1,
				null,
				null);
	}
	
	@PostMapping("/operation/transaction")
	public Mono<Double> operationTransaction(OperationData operationData){
		return accountService.amountUpdate(
				operationData.getAccountId(),
				operationData.getCustomerId(),
				operationData.getAccountChange(), 
				2,
				operationData.getDestinyAccountId(),
				operationData.getDestinyCustomerId());
	}
	
	//This method save or update a record from the account database
	@PostMapping("/save")
	public Mono<Account> save(@RequestBody Account account)	{
		return accountService.save(account);
	}
	@DeleteMapping("/delete")
	public Mono<Void> delete(@RequestBody Account account){
		return accountService.delete(account);
	}
	
	@GetMapping("/customer/{id}")
	public Flux<Account> findByCustomerId(@PathVariable String id){
		return accountService.findByCustomerId(id);
	}
	
	@GetMapping("/created/between/{dateA}/{dateB}")
	public Flux<Account> searchCreditByCreationDateBefore(@PathVariable LocalDate dateA,@PathVariable LocalDate dateB){
		return accountService.findByCreateAtBetween(dateA, dateB);
	}
	

}
