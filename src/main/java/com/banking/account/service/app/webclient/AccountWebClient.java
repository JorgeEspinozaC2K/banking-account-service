package com.banking.account.service.app.webclient;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.banking.account.service.app.entity.Customer;

import reactor.core.publisher.Mono;

public class AccountWebClient {

	private Builder accountWebClient = WebClient.builder();
	
	public Mono<Customer> findCustomer(String id){
		return accountWebClient.build()
				.get()
				.uri("http://localhost:8082/api/v1/customer/{id}",id)
				.retrieve()
				.bodyToMono(Customer.class);
	}
}
