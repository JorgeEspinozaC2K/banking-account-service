package com.banking.account.service.app.Service;

import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
	
	//view the records
	public Flux<Account> findAll();
	//view a record
	public Mono<Account> findById(String id);
	//modify a record
	public Mono<Account> save(Account account);
	//delete a record
	public Mono<Void> delete(Account account);
}
