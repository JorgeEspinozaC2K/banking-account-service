package com.banking.account.service.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;

public interface AccountRepository extends ReactiveMongoRepository<Account, String>{

	/**
	* EN: This method find all the customers depending of they status of legal customer.
	*
	* ES: Este método encuentra a todos los clientes dependiendo de su estatus como
	* cliente legal.
	*
	* @param legalCustomer Boolean
	* @return Flux type Customer
	* */
	public Flux<Account> findByLegalAccount(Boolean legalAccount);

	/**
	* EN: This method find all the customers who belong to a determinate employing entity.
	*
	* ES: Este método encuentra a todos los clientes que pertenecen a una determinada entidad
	* empleadora.
	*
	* @param tributaryIdentifier Integer
	* @return Flux type Customer
	* */
	public Flux<Account> findByTributaryIdentifier(int tributaryIdentifier);
}
