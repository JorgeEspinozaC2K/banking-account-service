package com.banking.account.service.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.account.service.app.Service.AccountService;
import com.banking.account.service.app.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/account")

public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@GetMapping
	public Flux<Account> index() {
		return accountService.findAll();
	}
	
	@PostMapping("/save")
	public Mono<Account> save(@RequestBody Account account)	{
		return accountService.save(account);
	}

}
