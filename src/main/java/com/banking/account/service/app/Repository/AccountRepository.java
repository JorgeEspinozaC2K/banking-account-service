package com.banking.account.service.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.banking.account.service.app.model.Account;

public interface AccountRepository extends ReactiveMongoRepository<Account, String>{

}
