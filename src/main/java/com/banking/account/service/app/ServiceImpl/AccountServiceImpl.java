package com.banking.account.service.app.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.service.app.Repository.AccountRepository;
import com.banking.account.service.app.Service.AccountService;
import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	//dependency injection required for the class work
	private AccountRepository accountRepository;
	
	@Override
	//returns a data stream, 
	public Flux<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	//return a record
	public Mono<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	//returns a saved record
	public Mono<Account> save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	//Returns a void record
	public Mono<Void> delete(Account account) {
		return accountRepository.delete(account);
	}
}
