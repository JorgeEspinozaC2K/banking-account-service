package com.banking.account.service.app.service.imp;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.account.service.app.entity.AccountReduced;
import com.banking.account.service.app.entity.Card;
import com.banking.account.service.app.entity.Customer;
import com.banking.account.service.app.model.Account;
import com.banking.account.service.app.repository.AccountRepository;
import com.banking.account.service.app.service.AccountService;
import com.banking.account.service.app.webclient.AccountWebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImp implements AccountService {

	private static final Logger log = LoggerFactory.getLogger(AccountServiceImp.class);
	
	@Autowired
	//dependency injection required for the class work
	private AccountRepository accountRepository;
	
	private AccountWebClient accountWebClient = new AccountWebClient();
	
	@Override
	//returns a data stream, 
	public Flux<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	//return a record
	public Mono<Account> findById(String id) {
		return accountRepository.findById(id).defaultIfEmpty(new Account())
				.flatMap(acc -> acc.getId() == null ? Mono.error(new InterruptedException("Account not found")) :
					Mono.just(acc))
				.doOnError(ex -> log.error(ex.getMessage()))
				.onErrorResume(err ->Mono.empty());
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
		
		return accountRepository.findAll()
				.filter(acc -> acc.getOwners().stream().anyMatch(c->c.getId().equalsIgnoreCase(customerId))
						|| acc.getAuthorities().stream().anyMatch(c->c.getId().equalsIgnoreCase(customerId)))
				.defaultIfEmpty(new Account())
				.flatMap(_account -> _account.getOwners()==null ?
						Mono.error(new InterruptedException("Any account with this customer id does not exist")):
						Mono.just(_account)
				).onErrorResume(_ex ->{
					log.error(_ex.getMessage());
					return Mono.empty();
				});
		
	}
	@Override
	public Flux<Account> findByCreateAtBetween(LocalDate createAtF, LocalDate createAtL) {
		return accountRepository.findByCreateAtAfter(createAtF)
				.filter(a-> a.getCreateAt().isBefore(createAtL==null? LocalDate.now(): createAtL));
	}

	/**
	 * Metodo para enlazar una cuenta existente a una nueva tarjeta de debito
	 */
	@Override
	public Mono<Card> linkAccountToNewCard(String accountId,String customerId) {
				
		return accountRepository.findById(accountId)
		.defaultIfEmpty(new Account())
		.flatMap(acc ->{
			if (acc.getId() == null) {
				return Mono.error(new InterruptedException("Any account can be found"));
			}
			else {
				return accountWebClient.findCustomer(customerId)
						.defaultIfEmpty(new Customer())
						.flatMap(cus -> {
							if (cus.getId() == null) {
								return Mono.error(new InterruptedException("Customer not found"));
							}
							else {
								return Mono.just(acc)
										.flatMap( accData ->{
											if (accData.getAuthorities().stream().anyMatch(c->c.getId().equalsIgnoreCase(customerId))
													|| accData.getOwners().stream().anyMatch(c->c.getId().equalsIgnoreCase(customerId))) {
												return accountWebClient.createCard(cardNumberCreation(0L), customerId, accountId);
											}
											else {
												return Mono.error(new InterruptedException("Data does not correspond"));
											}
										});
							}
						});
			}
		});
	}
	
	@Override
	public Flux<Card> linkAccountToExistingCards(String accountId, String customerId) {
				
		return findByCustomerId(customerId)
		.defaultIfEmpty(new Account())
		.flatMap(acc ->{
			if (acc.getId() == null) {
				return Mono.error(new InterruptedException("Any account can be found"));
			}
			else {
				
				Mono<List<Card>> allCards = accountWebClient.findCustomerCards(customerId)
						.switchIfEmpty(Flux.error(new InterruptedException("Customer not found")))
						.collectList();
				
				for(Card c : allCards.block()) {
					List<AccountReduced> basicList = c.getAllAccounts();
					basicList.add(new AccountReduced(accountId));
					c.setAllAccounts(basicList);
					accountWebClient.updateCustomerCards(c);
				}
				return accountWebClient.findCustomerCards(customerId);
			}
		});
	}

	public Long cardNumberCreation(Long cardNumber) {
		Long crn = Long.parseLong(String.format("%16d",ThreadLocalRandom.current().nextLong(9999999999999999L)));
		if (cardNumber == 0L) {
			return cardNumberCreation(Math.abs(crn));
		}else {
			Long cn = accountWebClient.findCard(cardNumber)
					.defaultIfEmpty(new Card())
					.block().getCardNumber();
			
			return cn == null ? cn : cardNumberCreation(Math.abs(crn));
		}
	}

	@Override
	public Mono<Double> amountConsult(String accountId) {
		return accountRepository.findById(accountId)
				.defaultIfEmpty(new Account())
				.flatMap(a -> a.getId()==null ? Mono.error(new InterruptedException("Account not found")):
					Mono.just(a.getAmountLeft()));
	}

	@Override
	public Mono<Double> amountUpdate(String accountId, String customerId, Double accountChange, Integer operation, String destinyAccountId, String destinyCustomerId) {
		
		
		Mono<List<Account>> customerAllAccounts = findByCustomerId(customerId).collectList();
		
		if (customerAllAccounts == null) {
			return Mono.error(new InterruptedException("Accounts not found"));
		}
		switch (operation) {
		case 0:
			Integer accountWitdrawCounter = 0;
			for(Account account :customerAllAccounts.block()) {
				accountWitdrawCounter++;
				if (account.getAmountLeft() >= accountChange) {
					account.setAmountLeft(account.getAmountLeft()  - accountChange);
					return Mono.just(accountRepository.save(account).block().getAmountLeft());
				}
				else {
					if(accountWitdrawCounter == customerAllAccounts.block().size()) {
						return Mono.error(new InterruptedException("Operation error not enought funds"));
					}
				}
			}
			break;
		case 1:
			for(Account account :customerAllAccounts.block()) {
				if (account.getId() == accountId) {
					account.setAmountLeft(account.getAmountLeft()  + accountChange);
					return Mono.just(accountRepository.save(account).block().getAmountLeft());
				}
			}
			break;
		case 2:
			Integer accountTransferCounter = 0;
			for(Account account :customerAllAccounts.block()) {
				if (account.getAmountLeft() >= accountChange) {
					account.setAmountLeft(account.getAmountLeft()  - accountChange);
					amountUpdate(destinyAccountId,destinyCustomerId,accountChange,1,null,null);
					return Mono.just(accountRepository.save(account).block().getAmountLeft());
				}
				else {
					if(accountTransferCounter == customerAllAccounts.block().size()) {
						return Mono.error(new InterruptedException("Operation error not enought funds"));
					}
				}
			}
			break;
		default:
			return Mono.error(new InterruptedException("Operation inexistent"));
		}
		return Mono.error(new InterruptedException("Operation error"));
	}
	
	/**
	 * if (customer.getIsTributary()) {
					customer.setIsPyme(customer.getIsVip() ? false : customer.getIsPyme());
					customer.setPersonalIdentifier(null);
				}

				customer.setTributaryIdentifier(!customer.getIsTributary() ? null : customer.getTributaryIdentifier());

				customer.setIsVip(customer.getIsPyme() ? false : customer.getIsVip());
	 */
}
