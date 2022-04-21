package com.banking.account.service.app.service;

import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
	
	//view the records
	/**
	 * This method bring all the information about the accounts in the database
	 * @return Flux type Account
	 */
	public Flux<Account> findAll();
	
	//view a record
	/**
	 * This method bring a record from the account database
	 * @return Mono type account
	 */
	public Mono<Account> findById(String id);
	//modify a record
	/**
	 * This method save or update record from the account database
	 * @returns Mono type account
	 */
	public Mono<Account> save(Account account);
	//delete a record
	/**
	 * This method delete a record from the account database
	 * @return Mono Void account
	 */
	public Mono<Void> delete(Account account);
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	public Flux<Account> findByCustomerId(String customerId);
}
