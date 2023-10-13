package com.springboot.webflux.graphQL.controllers;

import com.springboot.webflux.graphQL.SuppressUnusedParameterWarning;
import com.springboot.webflux.graphQL.dto.Account;
import com.springboot.webflux.graphQL.dto.Customer;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class AccountController {

    @SchemaMapping
    @SuppressUnusedParameterWarning
    public Mono<Account> account(Customer customer) {
        String type = ThreadLocalRandom.current().nextBoolean() ? "SAVING" : "CHECKING";
        return Mono.just(
                Account.build(
                        UUID.randomUUID(),
                        ThreadLocalRandom.current().nextInt(100000, 1000000),
                        type
                ));
    }
@BatchMapping(typeName = "Customer",field = "accountBatch")
    public Flux<Account> accounts(List<Customer> customer) {
    String type =ThreadLocalRandom.current().nextBoolean()?"SAVING":"CHECKING";
        return Flux.fromIterable(customer).map(c-> Account.build(
                UUID.randomUUID(),
                ThreadLocalRandom.current().nextInt(100000,1000000),
                type
                )).defaultIfEmpty(Account.build());
    }
}
