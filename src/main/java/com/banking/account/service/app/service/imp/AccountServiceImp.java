package com.banking.account.service.app.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.service.app.model.Account;
import com.banking.account.service.app.repository.AccountRepository;
import com.banking.account.service.app.service.AccountService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImp implements AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImp.class);
	
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
		
	  if (account.getId() != null) {
		  return accountRepository.findById(account.getId())
				  .defaultIfEmpty(new Account())
				  .flatMap(_account -> {
			if (_account.getId() == null) {
				return Mono.error(new InterruptedException("Can't update this account"));
			} else {
				account.setOffer(_account.getOffer());
				account.setAccountNumber(_account.getAccountNumber());
				account.setOwners(_account.getOwners());
				account.setAuthorities(_account.getAuthorities());
				account.setMonthMoves(_account.getMonthMoves());
				account.setWitdrawalDay(_account.getWitdrawalDay());
				account.setDepositDay(_account.getDepositDay());
				account.setCreateAt(_account.getCreateAt());
				return accountRepository.save(account);
			}
			}).onErrorResume(_ex ->{
				log.error(_ex.getMessage());
				return Mono.empty();
			});
	}else {
		return accountRepository.save(account);
		}
	}

	@Override
	//Returns a void record
	public Mono<Void> delete(Account account) {
		return accountRepository.delete(account);
	}

	@Override
	public Flux<Account> findByCustomerId(String customerId) {
		return accountRepository.findByCustomerId(customerId)
				.defaultIfEmpty(new Account())
				.flatMap(_account -> _account.getId() == null ?
						Mono.error(new InterruptedException("Not found")):
						Mono.just(_account)
						)
				.onErrorResume(_ex ->{
					log.error(_ex.getMessage());
					return Mono.empty();
				});
	}
}
