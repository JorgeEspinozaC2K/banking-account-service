package com.banking.account.service.app.repository;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

	/**
	 * EN: This method find all accounsts that are associayed with the id number ES:
	 * Este método encuentra todas las cuentas empresariales a los que se esta
	 * asociado con su número de identifación
	 * 
	 * @param personalIdentifier Integer
	 * @return flux type Customer
	 */
	public Flux<Account> findByAuthorities(Integer personalIdentifier);

	/**
	 * EN: This method returns all the accounts of an owners filtered by their
	 * identification number 
	 * ES: Este metodo retorna las cuentas de un propietario
	 * filtrado por su número de identificación
	 * 
	 * @param personalIdentifier integer
	 * @return flux type account
	 */
	public Flux<Account> findByOwners(String owners);

	/**
	 * EN: This method finds the details of a bank account ES: Este metodo encuentra
	 * los datos de una cuenta bancaria
	 * 
	 * @param accountNumber
	 * @return Mono type account
	 */
	public Mono<Account> findByAccountNumber(Long accountNumber);
	
	public Flux<Account> findByCreateAtAfter(LocalDate createAtL);

}
