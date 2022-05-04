package com.banking.account.service.app.webclient;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.banking.account.service.app.entity.Card;
import com.banking.account.service.app.entity.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AccountWebClient {

	private Builder accountWebClient = WebClient.builder();

	public Mono<Customer> findCustomer(String id) {
		return accountWebClient
				.build()
				.get()
				.uri("http://localhost:8080/customer/{id}", id)
				.retrieve()
				.bodyToMono(Customer.class);
	}
	
	public Flux<Card> findCustomerCards(String id) {
		return accountWebClient
				.build()
				.get()
				.uri("http://localhost:8080/card/customer/{id}", id)
				.retrieve()
				.bodyToFlux(Card.class);
	}
	
	public Mono<Card> updateCustomerCards(Card card) {
		return accountWebClient
				.build()
				.post()
				.uri("http://localhost:8080/card/update", card)
				.retrieve()
				.bodyToMono(Card.class);
	}

	public Mono<Card> createCard(Long cardNumber, String customerId, String accountId) {
		Card _card = new Card();
		_card.setDebit(true);
		_card.setAccountId(accountId);
		_card.setCustomerId(customerId);
		_card.setCardNumber(cardNumber);
		_card.setCcv(Integer.parseInt(String.format("%3d", new Random().nextInt(99999))));
		_card.setExpiration(LocalDate.now());
		_card.setCreateAt(LocalDate.now());
		return accountWebClient
				.build()
				.post()
				.uri("http://localhost:8080/card/new", _card)
				.retrieve()
				.bodyToMono(Card.class);
	}
	
	public Mono<Card> findCard(Long cardNumber) {
		return accountWebClient
				.build()
				.get()
				.uri("http://localhost:8080/card/{id}", cardNumber)
				.retrieve()
				.bodyToMono(Card.class);
	}
}
