package com.banking.account.service.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
	public Flux<Account> findByTributaryIdentifier(Integer tributaryIdentifier);
	
	/**
	 * EN: This method find all accounsts that are associayed with the id number 
	 * ES: Este método encuentra todas las cuentas empresariales a los que se esta asociado con su número de identifación
	 * @param personalIdentifier Integer
	 * @return flux type Customer
	 */
	public Flux<Account> findByAuthoritiesPersonalIdentifier(Integer personalIdentifier);
	
	/**
	 * EN: This method returns all the accounts of an owners filtered by their identification number
	 * ES: Este metodo retorna las cuentas de un propietario filtrado por su número de identificación
	 * @param personalIdentifier integer
	 * @return flux type account
	 */
	public Flux<Account> findByOwnersPersonalIdentifier(Integer personalIdentifier);
	
	/**
	 * EN: This method finds the details of a bank account
	 * ES: Este metodo encuentra los datos de una cuenta bancaria
	 * @param accountNumber
	 * @return Mono type account
	 */
	public Mono<Account> findByAccountNumber(Long accountNumber);
}
